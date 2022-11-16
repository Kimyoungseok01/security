package com.gangwondog.openAPI.domain.user;

import com.gangwondog.openAPI.domain.code.CommonCodeEntity;
import com.gangwondog.openAPI.domain.file.FileEntity;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "tb_user")
@Entity
public class UserEntity {

  @Id
  @Column(name = "id", columnDefinition = "INT UNSIGNED")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "nickname")
  private String nickname;

  @JoinColumn(name = "profile_image_id")
  @ManyToOne
  private FileEntity profileImageId;

  @JoinColumn(name = "sns_type_code")
  @ManyToOne
  private CommonCodeEntity snsTypeCode;

  @Column(name = "sns_id")
  private String snsId;

  @JoinColumn(name = "auth_code")
  @ManyToOne
  private CommonCodeEntity authCode;

  @Column(name = "secession_at")
  private String secessionAt;

  @Column(name = "create_date")
  private Instant createDate;
}
