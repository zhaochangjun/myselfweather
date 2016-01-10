package com.myselfweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.myselfweather.app.db.MyselfWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*
 *��װ�������ݿ�ķ��� */
public class MyselfWeatherDB {
	//���ݿ�����
	private static final String DB_NAME="myself_weather";
	//���ݿ�汾
	private static final int VERSION=1;
	private static MyselfWeatherDB myselfWeatherDB;
	private SQLiteDatabase db;
	private MyselfWeatherDB(Context context) {
		super();
		MyselfWeatherOpenHelper dbHelper=new MyselfWeatherOpenHelper(context,
				DB_NAME, null, 1);
		//�������ݿ�
		db=dbHelper.getWritableDatabase();
	}
	//���MyselfWeatherDBʵ��������ģʽ��
	public synchronized static MyselfWeatherDB getInstance(Context context){
		if(myselfWeatherDB==null){
			
			myselfWeatherDB=new MyselfWeatherDB(context);
		}
		return myselfWeatherDB;	
	}
	//��ʡ�ݴ洢�����ݿ���
	public void saveProvince(Province province){
		if(province!=null){
			
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	//�����ݿ��ж�ȡʡ����Ϣ
	public List<Province> loadProvince(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		do{
			Province province=new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		}while(cursor.moveToNext());
		return null;
	}
	//��������Ϣ�������ݿ���
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	//�����ݿ��ȡ������Ϣ
	public List<City> loadCity(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		do {
			City city=new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(provinceId);
			list.add(city);
		} while (cursor.moveToNext());
		return list;
		
	}
	//������ص���Ϣ�������ݿ���
	public void saveCounty(County county){
		if(county!=null){
			
			ContentValues values=new ContentValues();
			values.put("couny_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	//�����ݿ��ȡ�ص���Ϣ
	public List<County> loadCounty(int cityId){
		Cursor cursor=db.query("County", null, "cityID=?", new String[]{String.valueOf(cityId)}, null, null, null);
		List<County> list=new ArrayList<County>();
		do {
			County county=new County();
			county.	setId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCityId(cityId);
			list.add(county);
		} while (cursor.moveToNext());
		return list;
		
	}
}