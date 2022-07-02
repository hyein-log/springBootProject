package com.kosta.myapp.vo.relation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tbl_pdsfiles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PDSFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fno;
	private String pdsfilename;
}
