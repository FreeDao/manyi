package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: Intermediary
 *	中介号码库
 */
@Data
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Intermediary implements Serializable {

	   
	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Intermediary")
	@TableGenerator(name = "Intermediary", allocationSize = 1)
	private int id;
	@Column(unique = true, length = 11)
	private String mobile;
	private static final long serialVersionUID = 1L;

   
}
