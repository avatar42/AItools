package com.dea42.aitools.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Title: detections Bean <br>
 * Description: Class for holding data from the detections table. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 * Table name: detections<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br> */
@Data
@Entity
@Table(name = "`detections`")
public class Detections implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Classid")
	private Integer classid;
	@Column(name = "Confidence")
	private BigDecimal confidence;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "Picid")
	private Integer picid;
	@Column(name = "XMax")
	private BigDecimal xmax;
	@Column(name = "XMin")
	private BigDecimal xmin;
	@Column(name = "YMax")
	private BigDecimal ymax;
	@Column(name = "YMin")
	private BigDecimal ymin;
}
