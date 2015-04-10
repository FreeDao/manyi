package com.manyi.hims.entity;

import com.manyi.hims.entity.Area;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 区域（例如：古北、北新径），一般其父区域为Town Entity implementation class for Entity: Zone
 * 
 */
@Entity
public class Zone extends Area implements Serializable {

	private static final long serialVersionUID = 1L;
}
