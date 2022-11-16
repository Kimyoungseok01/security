package com.gangwondog.openAPI.domain.place;

import com.gangwondog.openAPI.domain.user.UserEntity;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tb_visit_stamp")
@Entity
public class VisitStampEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, columnDefinition = "INT UNSIGNED")
  private Long id;

  @JoinColumn(name = "user_id")
  @ManyToOne
  private UserEntity userId;

  @JoinColumn(name = "place_id")
  @ManyToOne
  private PlaceEntity placeId;

  @Column(name = "create_date")
  private Instant createDate;

}
