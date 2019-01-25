package com.ray.tech.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class PDFUtil {
    private static final String CONSTANT_PATH = "./uploadPDF";

    /**
     *
     * @param bill
     * @throws Exception
     */
    public static String genePdf(ContractBill bill) {
        try {
            Document doc = new Document();
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font headFont = new Font(baseFont, 18, Font.BOLD);//设置字体大小  样式
            Font keyFont = new Font(baseFont, 18, Font.BOLD);//文字加粗
            Font title = new Font(baseFont, 24, Font.BOLD);//文字加粗
            Font textFont = new Font(baseFont, 22, Font.NORMAL);//正常文字
            Font contextFont = new Font(baseFont, 20, Font.NORMAL);//正常文字
            String path = String.format(CONSTANT_PATH, bill.getBorrower().getMobile(), bill.getId());
            String fileName = bill.getId() + ".pdf";
            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }
            PdfWriter.getInstance(doc, new FileOutputStream(path + fileName));
            doc.open();
            //TODO
            doc.add(new Paragraph("借款人: "));
            User borrower = bill.getBorrower();
            doc.add(new Paragraph(String.format("姓名:%s   身份证号: %s   手机: %s", borrower.getName(), borrower.getCredential().getIdentity(), borrower.getAccount()), contextFont));
            doc.add(new Paragraph("出借人: "));
            User lender = bill.getLender();
            doc.add(new Paragraph(String.format("姓名:%s   身份证号: %s   手机: %s", lender.getName(), lender.getCredential().getIdentity(), lender.getAccount()), contextFont));

            doc.add(new Paragraph("本金:" + bill.getBorrowedMoney() +
                    "  利率:" + bill.getInterestRate() +
                    "  应还日期:" + bill.getBorrowDate() +
                    "  到期日期:" + bill.getRepayDate(), contextFont));
            doc.close();
            return path + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    private static class User {

        String id = UidUtil.getUid();
        @JsonIgnore
        String pin;//交易密码

        //***************上上签字段******************//
        String account; //账号  必填
        String name; //用户名称  必填
        @JsonIgnore
        String userType = "1";//  必填
        String mail; //用户邮箱
        String mobile; //用户手机号码
        Credential credential = new Credential();//个人实名信息  必填
        @JsonIgnore
        String applyCert = "1";
        //注册返回异步申请证书任务id
        String taskId;
        //***************上上签字段******************//

        UserStatus userStatus = null;//用户注册状态,!!!!!首次打借条的时候再去注册!!!!!!
        String signImg;//签名文件地址
        String profileImg;   //头像
        Integer balance = 0;//余额(单位，分)
        CredentialState credentialState;//个人实名信息认证状态
        Long lastLoginTime;

        @Data
        public class Credential {
            String identity; //证件号码  必填
            String identityType = "0"; //证件类型  0-身份证
            String contactMail; //联系邮箱
            String contactMobile; //联系电话
            String province; //所在省份
            String city; //所在城市
            String address; //联系地址
        }

        public Credential createCredential() {
            return new Credential();
        }

        /**
         * 实名认证审核状态
         */
        public enum CredentialState {
            //未审核
            UNVERIFIED,
            //审核通过
            PASS,
            //审核中
            AUDITING
        }

        public enum UserStatus {
            NEW_APPLY(1),
            APPLYING(2),
            TIME_OUT(3),
            FAILED(4),
            SUCCESS(5),
            INVALID(-1),
            PROCESSING(0);
            Integer code;

            UserStatus(Integer code) {
                this.code = code;
            }

            UserStatus() {
            }

            public static UserStatus map(Integer code) {
                switch (code) {
                    case 1:
                        return NEW_APPLY;
                    case 2:
                        return APPLYING;
                    case 3:
                        return TIME_OUT;
                    case 4:
                        return FAILED;
                    case 5:
                        return SUCCESS;
                    case -1:
                        return INVALID;
                    case 0:
                        return PROCESSING;
                }
                return null;
            }
        }

        public User withoutImg() {
            this.setProfileImg(null);
            this.setSignImg(null);
            return this;
        }

        public User withoutProfile() {
            this.setProfileImg(null);
            return this;
        }
    }

    @Data
    private static class ContractUser {
        String name;
        String phone;
        Boolean change;
    }

    @Data
    private static class ContractBill {
        private String id = UidUtil.getUid();

        //todo
        private Integer fee = 1;//服务费(分)

        //**数字签名相关字段*********************************
        private String contractUrl;//合同文件路径
        private String contractId;//合同文件id
        //**数字签名相关字段*********************************
        private User lender;//出借人
        private User borrower;//借款人

        private Long borrowDate;//借款日
        private Long repayDate;//还款日

        private Long borrowedMoney;//借款金额
        private Long interestRate;//利率
        private Long repayment;//到期应还金额

        private Purpose purpose;//用途
        private String note;//备注
        private List<ContractUser> emergencyContacts;//紧急联系人
        private Long createTime;
        private ExamineStatus examineStatus;    //初始状态为未支付
        private Boolean del;

        //**支付相关字段****************************
        private BillStatus billStatus;
        private Boolean isSent;//微信是否回调
        private String timeEnd;
        // **支付相关字段****************************
        private String newestUpdTime;//最新操作时间
        private Long newRepayDate;//最新还款日期，为最新展期确认日期

        private Boolean isBorrower;  //是否为借款人   true：borrower   true: lender
        private Boolean isOverdue;  //是否逾期    true:已逾期   false：未逾期
        private Boolean isExtent;//是否展期   true：已展期 false:未展期
        private Boolean isUncheckExtent;//是否有未通过展期 true:有
        private String uncheckId;//未确认展期id
        private Integer searchCount;//条件查询的总记录数

        public ContractBill() {
        }

        public ContractBill(String billId) {
            this.id = billId;
        }


        public enum Purpose {
            DAILY,//日常
            BIRTHDAY,//生日
            ENTERTAINMENT,//娱乐
            TREATMENT,//医疗
            TOURISM,//旅游
            DECORATION,//装修
            EDUCATION,//教育
            WEDDING,//婚庆
            ELECTRONIC,//电子产品
            OTHERS//其他
        }


        public enum ExamineStatus {
            NOT_PAY,            //未支付
            PAY_SUCCESS,        //支付成功
            PAY_FAIL,           //支付失败
            CA_FAILED,          //ca失败
            PROCESSING,         //处理中
            UNCHECK,            //为检验
            EFFECTIVE,          //生效
            FINISH,             //已完成
            DENY                //拒绝
        }

        public enum BillStatus {
            NOT_PAY,//下单成功，但未付款  usage in OrderService.getWechatPayReturn()统一下单
            CLOSED,//关单
            PAY_ERROR,//支付错误
            REFUND,//退款
            FAILED,//下单失败   usage in OrderService.getWechatPayReturn()统一下单（默认）
            PAY_SUCCESS,//支付成功
            BALANCE_PAY_SUCCESS//余额支付成功
        }
    }
}
