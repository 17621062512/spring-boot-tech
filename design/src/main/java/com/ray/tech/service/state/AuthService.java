package com.ray.tech.service.state;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class AuthService {
    private State state;

    private int balance = 3;

    public AuthService() {
        state = new OfflineState(this);
    }

    public void auth() {
        state.auth();
    }

    public void success() {
        if (state.success()) {
            balance--;
            if (balance == 0) {
                state = new OutOfBalanceState(this);
            }
        }
    }

    public void failed() {
        state.failed();
    }

    public void offline() {
        state = new OfflineState(this);
    }
}
