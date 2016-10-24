

import com.git.domain.juhe.JuheResponse;
import com.git.domain.juhe.kuaidi.KuaiDiEntity;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class MenuServiceKuaiDiTest {
	public static void main(String[] args) {
		String json = "{'resultcode':'200','reason':'成功的返回','result':{'company':'顺丰','com':'sf','no':'575677355677','status':'1','list':[{'datetime':'2013-06-25 10:44:05','remark':'已收件','zone':'台州市'},{'datetime':'2013-06-27 08:51:00','remark':'签收人是：已签收','zone':'西安市/咸阳市'}]},'error_code':0}";
		JuheResponse<KuaiDiEntity> juheResponse = new Gson().fromJson(json, new TypeToken<JuheResponse<KuaiDiEntity>>() {}.getType());
		System.out.println(juheResponse);
	}
}
