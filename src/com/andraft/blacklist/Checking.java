package com.andraft.blacklist;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;

import com.andraft.adapter.Phone;
import com.andraft.adapter.Sms;
import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;
import com.andraft.utils.CallLogUtils;
import com.andraft.utils.SmsLogUtils;

public class Checking {

	private DataBase db;
	private Context context;
    private CallLogUtils calllog;
    private SmsLogUtils smslog;
	private static volatile Checking instance;
	private Phone[] phones;
	private int count = 0;
	private HashMap<String,String> minus_number,newka,newsms,minus_sms,contacts;
	private List<NumberModel> list;
	private Sms[] sms;

	public static Checking getInstance(Context context) {
		Checking localInstance = instance;
		if (localInstance == null) {
			synchronized (Checking.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new Checking(context);
				}
			}
		}
		return localInstance;
	}

	private Checking(Context context) {
		this.context = context;
		db = new DataBase(context);
		calllog = new CallLogUtils(context);
		this.smslog = new SmsLogUtils(context);
	}


	public CallLogUtils getCalllog() {
		return calllog;
	}

	public DataBase getDb() {
		return db;
	}
	
	public Phone[] getAdapterPhone(){
		phones = new Phone[db.getAllNumbers().size()];
		for(NumberModel numbers:db.getAllNumbers()){
			phones[count] = new Phone(numbers.getName(),numbers.getNum(),numbers.getBool());
			count++;
		
		}
		count=0;
		return phones;	
	}
	
	public HashMap<String,String> list_minus_phone(){
		minus_number = calllog.readAll();
		list = db.getAllNumbers();
		for(NumberModel num:list){
			if(minus_number.containsKey(num.getNum()))
				minus_number.remove(num.getNum());
		}
		if(newka!=null){
		for(Entry<String,String> n:getNewka().entrySet())
				minus_number.put(n.getKey(), n.getValue());
		
		newka.clear();
		}
		return minus_number;
	}
	
	public HashMap<String, String> getNewka() {
		return newka;
	}

	public HashMap<String,String> addUnknNumber(String number){
		newka = new HashMap<String,String>();
		newka.put(number, "unknown");
		return newka;
		
	}


	public HashMap<String, String> sms_minus_contacts() {
		minus_sms = smslog.readAllSms();
		contacts = calllog.readAllContacts();
		for(Entry<String,String> c:contacts.entrySet()){
			if(minus_sms.containsKey(c.getKey())){
				minus_sms.remove(c.getKey());
			}
		}
		
		return minus_sms;
	}
	
	public HashMap<String,String> addRegSms(String Sms){
		newsms = new HashMap<String,String>();
		
		//newka.put(number, "unknown");
		return newsms;
	}
	
	public HashMap<String,String> getRegSms(){
		return newsms;
	}

	public Sms[] getAdapterSms() {
		sms = new Sms[db.getAllSms().size()];
		for(SmsModel smski:db.getAllSms()){
			sms[count] = new Sms(smski.getNum(),smski.getText(),smski.getBool());
			count++;
		
		}
		count=0;
		return sms;
	}
	


}
