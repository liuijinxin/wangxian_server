package com.fy.engineserver.datasource.buff;

import java.util.ArrayList;

import com.fy.engineserver.core.Game;
import com.fy.engineserver.sprite.Fighter;
import com.fy.engineserver.sprite.Player;
import com.fy.engineserver.sprite.monster.Monster;
import com.xuanzhi.tools.simplejpa.annotation.SimpleEmbeddable;

/**
 * 驱散一个中毒的buff
 */
@SimpleEmbeddable
public class Buff_QuShanZhongDu extends Buff{

	/**
	 * 通知此buff，生命开始，此方法调用后，才会调用心跳函数
	 */
	public void start(Fighter owner){
		if(owner instanceof Player){
			Player p = (Player)owner;
			Buff buffs[] = p.getActiveBuffs();
			for(int i = 0 ; i < buffs.length ; i++ ){
				if(buffs[i] instanceof Buff_ZhongDu || buffs[i] instanceof Buff_ZhongDuWuGong || buffs[i] instanceof Buff_ZhongDuFaGong || buffs[i] instanceof Buff_ZhongDuZuiHouFaZuoWuGong || buffs[i] instanceof Buff_ZhongDuZuiHouFaZuoFaGong){
					buffs[i].setInvalidTime(0);
					break;
				}
			}
		}else if(owner instanceof Monster){
			Monster s = (Monster)owner;
			ArrayList<Buff> buffs = s.getBuffs();
			for(int i = 0 ; i < buffs.size() ; i++ ){
				Buff buff = buffs.get(i);
				if(buff instanceof Buff_ZhongDu || buff instanceof Buff_ZhongDuWuGong || buff instanceof Buff_ZhongDuFaGong || buff instanceof Buff_ZhongDuZuiHouFaZuoWuGong || buff instanceof Buff_ZhongDuZuiHouFaZuoFaGong){
					buff.setInvalidTime(0);
					break;
				}
			}
		}
	}
	
	
	/**
	 * 通知此buff，生命结束，此方法调用后，就不会再调用心跳函数了
	 */
	public void end(Fighter owner){
	}
	
	/**
	 * 心跳函数
	 */
	public void heartbeat(Fighter owner,long heartBeatStartTime, long interval, Game game){
		super.heartbeat(owner, heartBeatStartTime, interval, game);
	}

}