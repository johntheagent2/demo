package com.sparkminds.elasticSearch.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TyreEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class TyreEntity_ {

	public static final String YEAR = "year";
	public static final String LOAD_INDEX = "loadIndex";
	public static final String RIM = "rim";
	public static final String NAME = "name";
	public static final String WIDTH = "width";
	public static final String PATTERN = "pattern";
	public static final String ID = "id";
	public static final String BRAND = "brand";
	public static final String HEIGHT = "height";

	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#year
	 **/
	public static volatile SingularAttribute<TyreEntity, String> year;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#loadIndex
	 **/
	public static volatile SingularAttribute<TyreEntity, String> loadIndex;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#rim
	 **/
	public static volatile SingularAttribute<TyreEntity, Integer> rim;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#name
	 **/
	public static volatile SingularAttribute<TyreEntity, String> name;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#width
	 **/
	public static volatile SingularAttribute<TyreEntity, Integer> width;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#pattern
	 **/
	public static volatile SingularAttribute<TyreEntity, String> pattern;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#id
	 **/
	public static volatile SingularAttribute<TyreEntity, Long> id;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity
	 **/
	public static volatile EntityType<TyreEntity> class_;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#brand
	 **/
	public static volatile SingularAttribute<TyreEntity, String> brand;
	
	/**
	 * @see com.sparkminds.elasticSearch.entity.TyreEntity#height
	 **/
	public static volatile SingularAttribute<TyreEntity, Integer> height;

}

