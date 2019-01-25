package com.ray.tech.service;

import com.google.common.collect.*;
import com.ray.tech.constant.FileConstant;
import com.ray.tech.mock.TableMockData;
import com.ray.tech.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TableDemoService {

    public String getStatisticTable() {
        Table<String, String, Integer> table = TreeBasedTable.create();
        initTable(table);
        List<String> dataRows = TableMockData.getMockTableRawData();
        addDataToTable(dataRows, table);
        return printTable(table);
    }

    public void writeToLocal() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "table_data_" + sdf.format(new Date()) + FileConstant.Suffix.CSV;
        File file = new File(FileConstant.STATISTIC_CSV_PATH + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Table<String, String, Integer> table = TreeBasedTable.create();
        initTable(table);
        List<String> dataRows = TableMockData.getMockTableRawData();
        addDataToTable(dataRows, table);
        List<String> lineData = getLineDataFromTable(table);
        for (String line : lineData) {
            FileUtils.write(file, line, true);
        }
    }

    private void addDataToTable(List<String> dataRows, Table<String, String, Integer> table) {
        for (String row : dataRows) {
            String[] split = row.split(",");
            addToTable(Double.valueOf(split[1]), Double.valueOf(split[2]), table);
        }
    }

    private void initTable(Table table) {
        String[] col = {"a12.6-100", "a10.5-12.6", "9.08-10.5", "8.03-9.08", "7.17-8.03",
                "6.45-7.17", "5.76-6.45", "5.09-5.76", "4.37-5.09", "0-4.37"};

        String[] row = {"4.89-100", "4.17-4.89", "3.7-4.17", "3.36-3.7", "3.08-3.36",
                "2.83-3.08", "2.56-2.83", "2.3-2.56", "1.94-2.3", "0-1.94"};

        for (int i = (row.length - 1); i >= 0; i--) {
            for (int j = (col.length - 1); j >= 0; j--) {
                table.put(row[i], col[j], 0);
            }
        }
    }

    private void addToTable(double mfN, double mgN, Table<String, String, Integer> table) {
        String mfPosition = getMFPosition(mfN);
        String mgPosition = getMGPosition(mgN);
        if (table.contains(mfPosition, mgPosition)) {
            Integer count = table.get(mfPosition, mgPosition);
            count++;
            table.put(mfPosition, mgPosition, count);
        } else {
            table.put(mfPosition, mgPosition, 0);
        }
    }

    /**
     * a\b 列1,列2,列3,列4,列5,列6
     * 行1
     * 行2
     * 行3
     * 行4
     * 行5
     * 行6
     *
     * @param table 需要被打印的表
     * @return 根据需要自行转换为对应的类型
     */
    private String printTable(Table<String, String, Integer> table) {
        StringBuilder sb = new StringBuilder();
        Set<String> columnKeySet = table.columnKeySet();
        //********************表的首行***********************
        //e.g: a\b 列1,列2,列3,列4,列5,列6
        sb.append("mg\\mf").append(",");
        for (String columnKey : columnKeySet) {
            sb.append(columnKey).append(",");
        }
        //*********************每一行数据*********************
        Set<String> rowKeySet = table.rowKeySet();
        for (String row : rowKeySet) {
            sb.append(row);//第一列:行1 行2 行3 行4 行5 行6
            Map<String, Integer> map = table.row(row);
            for (String column : columnKeySet) {
                sb.append(",").append(map.get(column));//对应位置的值
            }
        }
        return sb.toString();
    }

    private static List<String> getLineDataFromTable(Table<String, String, Integer> table) {
        List<String> str = new ArrayList<>();
        StringBuilder title = new StringBuilder();
        Set<String> columnKeySet = table.columnKeySet();
        title.append("mg\\mf").append(",");
        for (String columnKey : columnKeySet) {
            title.append(columnKey).append(",");
        }
        str.add(title.toString());
        Set<String> rowKeySet = table.rowKeySet();
        for (String row : rowKeySet) {
            StringBuilder eachRow = new StringBuilder();
            eachRow.append(row);
            Map<String, Integer> rowData = table.row(row);
            for (String columnName : columnKeySet) {
                eachRow.append(",").append(rowData.get(columnName));
            }
            str.add(eachRow.toString());
        }
        return str;
    }

    private String getMFPosition(double mf) {
        RangeMap<Double, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(4.89, 100.0), "4.89-100");
        rangeMap.put(Range.closed(4.17, 4.89), "4.17-4.89");
        rangeMap.put(Range.closed(3.7, 4.17), "3.7-4.17");
        rangeMap.put(Range.closed(3.36, 3.7), "3.36-3.7");
        rangeMap.put(Range.closed(3.08, 3.36), "3.08-3.36");
        rangeMap.put(Range.closed(2.83, 3.08), "2.83-3.08");
        rangeMap.put(Range.closed(2.56, 2.83), "2.56-2.83");
        rangeMap.put(Range.closedOpen(2.3, 2.56), "2.3-2.56");
        rangeMap.put(Range.closedOpen(1.94, 2.3), "1.94-2.3");
        rangeMap.put(Range.closedOpen(0.0, 1.94), "0-1.94");
        return rangeMap.get(mf);
    }

    private String getMGPosition(double mg) {
        RangeMap<Double, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(12.6, 100.0), "a12.6-100");
        rangeMap.put(Range.closed(10.5, 12.6), "a10.5-12.6");
        rangeMap.put(Range.closed(9.08, 10.5), "9.08-10.5");
        rangeMap.put(Range.closed(8.03, 9.08), "8.03-9.08");
        rangeMap.put(Range.closed(7.17, 8.03), "7.17-8.03");
        rangeMap.put(Range.closed(6.45, 7.17), "6.45-7.17");
        rangeMap.put(Range.closed(5.76, 6.45), "5.76-6.45");
        rangeMap.put(Range.closedOpen(5.09, 5.76), "5.09-5.76");
        rangeMap.put(Range.closedOpen(4.37, 5.09), "4.37-5.09");
        rangeMap.put(Range.closedOpen(0.0, 4.37), "0-4.37");
        return rangeMap.get(mg);
    }
}
