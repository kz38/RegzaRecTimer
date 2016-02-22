package jp.os.kz.android.regza_app;

		import java.util.regex.Matcher;
		import java.util.regex.Pattern;

//クラスのインスタンスを1つに保つ（Singletonパターン）
public class TvChannel {
	//Start シングルトンパターン
	// このクラスに唯一のインスタンス
	private static TvChannel instance = new TvChannel();

	private TvChannel() {}

	// インスタンス取得メソッド
	public static TvChannel getInstance() {
		return instance;
	}
//End  シングルトンパターン

// 以後、通常のフィールドやメソッドの宣言
	    /*private String _UserName = "";
	    public void setUserName(String sUserName) {
	        this._UserName = sUserName;
	    }
	    public String getUserName() {
	        return _UserName;
	    }*/

	private String[][] _TvChannelList={
			{"チャンネル選択",""},			/*0*/
			{"NHK","TD011"},			/*1*/
			{"Eテレ","TD021"},			/*2*/
			{"サンテレビ","TD031"},		/*3*/
			{"毎日放送","TD041"},		/*4*/
			{"KBS京都","TD051"},			/*5*/
			{"ABC","TD061"},			/*6*/
			{"テレビ大阪","TD071"},		/*7*/
			{"関西テレビ","TD081"},		/*8*/
			{"読売テレビ","TD101"},		/*9*/
			{"NHK BS1","BS101"},		/*10*/
			{"NHK BS2","BS102"},		/*11*/
			{"NHK h","BS103"},			/*12*/
			{"BS日テレ","BS141"},			/*13*/
			{"BS朝日","BS151"},			/*14*/
			{"BS-TBS","BS161"},			/*15*/
			{"BSジャパン","BS171"},		/*16*/
			{"BSフジ","BS181"},			/*17*/
			{"WOWOW","BS191"},			/*18*/
			{"スター・チャンネル","BS200"},	/*19*/
			{"BS11","BS211"},			/*20*/
			{"TwellV","BS222"}			/*21*/
	};
	public String[][] getTvChannelList(){
		return _TvChannelList;
	}
	    /*]

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
	    */

	public int searchChannel(String str){
		int ret = 0;
		int i=0;
		Pattern pattern = Pattern.compile("^"+str);
		Matcher matcher;
		boolean blnMatch;
		for (String[] a : _TvChannelList){
			matcher = pattern.matcher(a[0]);
			blnMatch= matcher.matches();
			if (blnMatch==true){
				ret=i;
				break;
			}
			i++;
		}
		return ret;
	}
}