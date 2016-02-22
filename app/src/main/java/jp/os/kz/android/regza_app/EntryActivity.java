package jp.os.kz.android.regza_app;

		import java.util.Calendar;
		import java.util.regex.Matcher;
		import java.util.regex.Pattern;

		import android.app.Activity;
		import android.app.DatePickerDialog;
		import android.app.TimePickerDialog;
		import android.content.Intent;
		import android.content.SharedPreferences;
		import android.net.Uri;
		import android.os.Bundle;
		import android.preference.PreferenceManager;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.view.WindowManager;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemSelectedListener;
		import android.widget.ArrayAdapter;
		import android.widget.Button;
		import android.widget.DatePicker;
		import android.widget.EditText;
		import android.widget.Spinner;
		import android.widget.TimePicker;

public class EntryActivity extends Activity {

	TvChannel tvchannel;
	String[][] channelList;

	Calendar calendar;
	int year=0;
	int month=0;
	int day=0;
	int hour=0;
	int minute=0;

	String strDate="";
	boolean blnDate = false;
	int tpState=0;//開始時間：0 、終了時間：1

	String mailSubject = "";
	String mailText = "";

	Spinner spinner1;
	EditText editText8;
	EditText editText9;
	DatePickerDialog datePickerDialog;
	TimePickerDialog timePickerDialog;
	Button button1;
	Button buttonDate;
	Button buttonStartTime;
	Button buttonEndTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Activity起動時に ソフトキーボードを自動で立ち上げない
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setContentView(R.layout.entry);

		//現在の時刻を取得
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		// 画面の XML を指定する
		CharSequence exSubject = "";
		CharSequence exText = "";
		String str="";
		String strSplit[] = null;
		Integer indexNum=0;
		Integer dateIndexNum=0;

		spinner1 = (Spinner)findViewById(R.id.spinner1);
		editText8 =(EditText)findViewById(R.id.editText8);
		editText9 =(EditText)findViewById(R.id.editText9);

		button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				button1_onClick();
			}
		});


		//日付設定ダイアログ作成
		datePickerDialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view,
										  int year, int monthOfYear, int dayOfMonth) {
						// setボタンが押されたときの処理を書き込む
						buttonDate.setText(
								String.valueOf(year) + "/" +
										String.valueOf(monthOfYear + 1) + "/" +
										String.valueOf(dayOfMonth));
					}
				},
				year, month, day);

		//予約番組の日付ボタン
		buttonDate = (Button)findViewById(R.id.buttonDate);
		buttonDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String sdate="";
				String adate[];
				try{
					sdate=(String) buttonDate.getText();
					adate=sdate.split("/");
					datePickerDialog.updateDate(Integer.valueOf(adate[0]),
							Integer.valueOf(adate[1])-1,
							Integer.valueOf(adate[2]));
				}
				//全ての例外をキャッチ
				catch(Exception ex){
					datePickerDialog.updateDate(year,month,day);
				}
				finally{
					datePickerDialog.show();
				}
			}
		});

		//時間設定ダイアログ作成
		timePickerDialog = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						//変数 tpState の値によって処理を分岐する
						//0 の場合は、buttonStartTime のテキストに値をセット
						//1 の場合は、buttonEndTime のテキストに値をセット
						try{
							String sMinute=String.valueOf(minute);
							if(minute<10){sMinute="0"+sMinute;}
							switch(tpState){
								case 0:
									buttonStartTime.setText(
											String.valueOf(hourOfDay) + ":" +
													sMinute);
									break;
								case 1:
									buttonEndTime.setText(
											String.valueOf(hourOfDay) + ":" +
													sMinute);
									break;
							}
						}
						catch(Exception ex){

						}
					}
				}, hour, minute, true);

		//予約番組の開始時間ボタン
		buttonStartTime = (Button)findViewById(R.id.buttonStartTime);
		buttonStartTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				buttonStartTime_onClick();
			}

			private void buttonStartTime_onClick() {
				// TODO Auto-generated method stub
				String stime="";
				String atime[];
				int h=0;
				int m=0;
				tpState=0;
				try{
					stime=(String)buttonStartTime.getText();
					atime=stime.split(":");
					if(stime==""){
						h=hour;
						m=minute;
					}else{
						h=Integer.valueOf(atime[0]);
						m=Integer.valueOf(atime[1]);
					}
					timePickerDialog.updateTime(h,m);
				}
				catch(Exception ex){

				}
				finally{
					timePickerDialog.show();
				}
			}
		});

		//予約番組の終了時間ボタン
		buttonEndTime = (Button)findViewById(R.id.buttonEndTime);
		buttonEndTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				buttonEndTime_onClick();
			}

			private void buttonEndTime_onClick() {
				// TODO Auto-generated method stub
				String stime="";
				String atime[];
				int h=0;
				int m=0;
				tpState=1;
				try{
					stime=(String)buttonEndTime.getText();
					atime=stime.split(":");
					if(stime==""){
						h=hour;
						m=minute;
					}else{
						h=Integer.valueOf(atime[0]);
						m=Integer.valueOf(atime[1]);
					}
					timePickerDialog.updateTime(h,m);
				}
				catch(Exception ex){

				}
				finally{
					timePickerDialog.show();
				}
			}
		});

		//チャンネルリスト取得
		tvchannel = TvChannel.getInstance();
		channelList = tvchannel.getTvChannelList();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		for (String[] a : channelList){
			adapter.add(a[0]);
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		//リスナーの登録
		spinner1.setOnItemSelectedListener(spinnerListner);

		if(( savedInstanceState == null ) || ( savedInstanceState.isEmpty()) )
		{
			// インテントから追加データを取り出す
			exSubject = getIntent().getExtras().getString( "EXTRA_SUBJECT");
			exText = getIntent().getExtras().getString( "EXTRA_TEXT");

			str=exText.toString();
			str=str.replace("\n", " ");
			strSplit = str.split(" ");
			//配列の中から年月日が格納されている要素番号をさがす
			Pattern pattern = Pattern.compile("[0-9]{1,4}/[0-9]{1,2}/[0-9]{1,2}");
			Matcher matcher;
			boolean blnMatch;
			Integer c = 0;
			//for-each文
			for (String s : strSplit){
				matcher = pattern.matcher(s);
				blnMatch= matcher.matches();
				if (blnMatch==true){
					dateIndexNum=c;
				}
				c++;
			}
		}

		//for-each文
		for (String s : strSplit){
			if (s.isEmpty() == false){
				//チャンネルの取得
				if (indexNum==(dateIndexNum-1)){
					int i = tvchannel.searchChannel(s.trim());
					spinner1.setSelection(i);
					//editText9.setText(channelList[i][1]);

					editText9.setText(s.trim());
				}
				//年月日の取得
				if (indexNum==dateIndexNum){
					buttonDate.setText(s);
				}
				//開始時間の取得
				if (indexNum==(dateIndexNum+1)){
					buttonStartTime.setText(s);
				}
				//終了時間の取得
				if (indexNum==(dateIndexNum+3)){
					buttonEndTime.setText(s);
				}
				indexNum++;
			}
		}
		// 	番組タイトルの取得
		editText8.setText(exSubject);
	}

	//Spinnerが選択された時の動作
	private OnItemSelectedListener spinnerListner = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
								   long id) {
			// TODO Auto-generated method stub
			Spinner spinner = (Spinner) parent;
			// 選択されたアイテムを取得
			String item = (String) spinner.getSelectedItem();
			int i = tvchannel.searchChannel(item);
			editText9.setText(channelList[i][1]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	};

	//button1 が押された時の動作
	private void button1_onClick(){
		//

		String extraText=getRegzaCommand();
		String mailTo="mailto:";
		SharedPreferences sharedPreferences =
				PreferenceManager.getDefaultSharedPreferences(this);
		mailTo += sharedPreferences.getString("editTextPref1", "");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SENDTO);
		// データを指定
		intent.setData(Uri.parse(mailTo));
		// CCを指定
		//intent.putExtra(Intent.EXTRA_CC, new String[]{"cc@example.com"});
		// BCCを指定
		//intent.putExtra(Intent.EXTRA_BCC, new String[]{"bcc@example.com"});
		// 件名を指定
		intent.putExtra(Intent.EXTRA_SUBJECT, editText8.getText().toString());
		// 本文を指定
		intent.putExtra(Intent.EXTRA_TEXT, extraText);

		startActivity(intent);
	}

	//メール本文に入力するレグザの予約設定データを取得する
	private String getRegzaCommand(){
		String ret="";
		String yyyy="";
		String mm="";
		String dd="";
		String shh="";
		String stt="";
		String ehh="";
		String ett="";
		String YYYYMMDD=(String) buttonDate.getText();
		String sHHTT=(String) buttonStartTime.getText();
		String eHHTT=(String) buttonEndTime.getText();
		String strSplit[];
		int i =0;
		SharedPreferences sharedPreferences =
				PreferenceManager.getDefaultSharedPreferences(this);

		//年月日の取得
		strSplit=YYYYMMDD.split("/");
		i =0;
		for (String s : strSplit){	//for-each文
			if(i==0){yyyy =s;}
			if(i==1){mm =s;}
			if(i==2){dd =s;}
			i++;
		}
		//開始時間の取得
		strSplit=sHHTT.split(":");
		i =0;
		for (String s : strSplit){	//for-each文
			if(i==0){shh =s;}
			if(i==1){stt =s;}
			i++;
		}
		//終了時間の取得
		strSplit=eHHTT.split(":");
		i =0;
		for (String s : strSplit){	//for-each文
			if(i==0){ehh =s;}
			if(i==1){ett =s;}
			i++;
		}
		//チャンネルの取得
		String channel =editText9.getText().toString();

		//コマンド
		ret += "dtvopen ";
		//パスワード
		ret += sharedPreferences.getString("editTextPref2", "") + " ";
		//年月日
		if (mm.length()==1){mm="0"+mm;}
		if (dd.length()==1){dd="0"+dd;}
		ret += yyyy + mm + dd + " ";
		//開始時間
		if (shh.length()==1){shh="0"+shh;}
		if (stt.length()==1){stt="0"+stt;}
		ret += shh + stt + " ";
		//終了時間
		if (ehh.length()==1){ehh="0"+ehh;}
		if (ett.length()==1){ett="0"+ett;}
		ret += ehh + ett + " ";
		//チャンネル
		ret += channel;

		return ret;
	}

}