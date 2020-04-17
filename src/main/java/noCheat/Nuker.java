package noCheat;

import java.util.Hashtable;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.scheduler.Task;

public class Nuker implements Listener{
	public Hashtable<String, Hashtable<Integer, Short>> list = new Hashtable<String, Hashtable<Integer,Short>>();
	public Hashtable<String, Short> detectCounter = new Hashtable<String, Short>(); //핵으로 감지된 횟수
	
	NoCheat owner;
	public Nuker(NoCheat owner) {
		this.owner = owner;
		
		// clean task
		owner.getServer().getScheduler().scheduleRepeatingTask(new Task() {
			
			@Override
			public void onRun(int currentTick) {
				cleanList();
			}
		}, 20 * 30, false);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void detectNuker(BlockBreakEvent ev) {
		Player player = ev.getPlayer();
		if(player.isOnline() == false) {
			return;
		}
		if(player.isOp()) {
			return;
		}
		if(player.getGamemode() == 1) {
			return;
		}
		if(getCount(player.getName()) >= 6) {
			if(getDetectCount(player.getName()) < 3) {
				this.addDetectCount(player.getName());
				this.setCount(player.getName(), (short)0);
			}else {
				player.kick("재접속 해주세요.\n코드 0");
				owner.sendMessage("@everyone" + player.getName() + "님이 ``광산핵(두더지핵)``으로 의심됩니다.\n 해당 플레이어의 현재 위치 : " + player.toString());
				ev.setCancelled(true);
				return;
			}
		}
		addCount(player.getName());
	}
	
	public int getTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public short getCount(String name) {
		Hashtable<Integer, Short> map = list.getOrDefault(name, new Hashtable<Integer, Short>());
		return map.getOrDefault(getTime(), (short)0);
	}
	
	public void addCount(String name) {
		Hashtable<Integer, Short> map = list.getOrDefault(name, new Hashtable<Integer, Short>());
		short count = map.getOrDefault(getTime(), (short)0);
		count++;
		
		map.put(getTime(), count);
		list.put(name, map);
	}
	
	public void setCount(String name, short count) {
		Hashtable<Integer, Short> map = list.getOrDefault(name, new Hashtable<Integer, Short>());
		
		map.put(getTime(), count);
		list.put(name, map);
	}
	
	public short getDetectCount(String name) {
		return this.detectCounter.getOrDefault(name, (short)0);
	}
	
	public void addDetectCount(String name) {
		detectCounter.put(name, (short) (getDetectCount(name) + 1));
	}
	
	public void cleanList() {
		list.clear();
		detectCounter.clear();
	}

}
