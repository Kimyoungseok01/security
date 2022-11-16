package com.gangwondog.openAPI.domain.code;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "tb_common_code")
@Entity
public class CommonCodeEntity {

  @Id
  @Column(name = "com_code")
  private String comCode;

  @JoinColumn(name = "group_code")
  @ManyToOne
  private CodeGroupEntity groupCode;

  @Column(name = "com_code_description")
  private String comCodeDescription;

  @Column(name = "order")
  private Integer order;

  @Column(name = "use_at")
  private String useAt;

  @Column(name = "create_date")
  private Instant createDate;

  @Column(name = "update_date")
  private Instant updateDate;

}
