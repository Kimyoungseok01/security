package com.gangwondog.openAPI.domain.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshTokenEntity {

    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public RefreshTokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshTokenEntity updateValue(String token) {
        this.value = token;
        return this;
    }
}
