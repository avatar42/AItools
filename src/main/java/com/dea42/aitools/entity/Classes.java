package com.dea42.aitools.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Title: classes Bean <br>
 * Description: Class for holding data from the classes table. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 * Table name: classes<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br> */
@Data
@Entity
@Table(name = "`classes`")
public class Classes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Active")
	private Integer active;
	@Column(name = "Catagory", length = 7)
	private String catagory;
	@Column(name = "Classname", length = 16)
	private String classname;
	@Column(name = "Grp", length = 12)
	private String grp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "Replacewith", length = 8)
	private String replacewith;
}
