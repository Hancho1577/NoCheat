package noCheat;

import cn.nukkit.plugin.PluginBase;
import hcDiscordBot.HcDiscordBot;

public class NoCheat extends PluginBase {
	private HcDiscordBot discordAPI;
	
	@Override
	public void onEnable() {
		if(this.getServer().getPluginManager().getPlugin("HcDiscordBot") != null) {
			this.discordAPI = (HcDiscordBot) this.getServer().getPluginManager().getPlugin("HcDiscordBot");
		}
		getServer().getPluginManager().registerEvents(new Nuker(this), this);
	}
	
	public void sendMessage(String content) {
		if(this.discordAPI != null) {
			discordAPI.sendAdministratorMessage(content);
		}
	}

}
