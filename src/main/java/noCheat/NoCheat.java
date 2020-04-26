package noCheat;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import hcDiscordBot.HcDiscordBot;

public class NoCheat extends PluginBase {
	private HcDiscordBot discordAPI;
	private Nuker nuker;
	
	@Override
	public void onEnable() {
		if(this.getServer().getPluginManager().getPlugin("HcDiscordBot") != null) {
			this.discordAPI = (HcDiscordBot) this.getServer().getPluginManager().getPlugin("HcDiscordBot");
		}
		Nuker nuker = new Nuker(this);
		this.nuker = nuker;
		getServer().getPluginManager().registerEvents(nuker, this);
	}

	@Override
	public void onDisable() {
		Config nukerData = new Config(this.getDataFolder().getAbsolutePath() + "/nukerQueue.yml", Config.YAML);
		nukerData.set("queue", this.nuker.queue);
		nukerData.save();
	}

	public void sendMessage(String content) {
		if(this.discordAPI != null) {
			discordAPI.sendAdministratorMessage(content);
		}
	}

}
