package com.gangwondog.openAPI.domain.code;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "tb_code_group")
@Entity
public class CodeGroupEntity {

  @Id
  @Column(name = "group_code", nullable = false)
  private String groupCode;

  @Column(name = "group_code_description")
  private String groupCodeDescription;

  @Column(name = "use_at")
  private String useAt;

  @Column(name = "create_date")
  private Instant createDate;

  @Column(name = "update_date")
  private Instant updateDate;

}
