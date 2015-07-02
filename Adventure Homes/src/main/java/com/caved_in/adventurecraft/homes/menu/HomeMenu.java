package com.caved_in.adventurecraft.homes.menu;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.adventurecraft.homes.HomeMessages;
import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.adventurecraft.homes.users.HomePlayerManager;
import com.caved_in.adventurecraft.homes.users.PlayerHomeAction;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.Wool;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuItem;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.SubMenuItem;
import com.caved_in.commons.warp.Warp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class HomeMenu extends ItemMenu {
	private static Title WELCOME_HOME_TITLE = new TitleBuilder().title("").subtitle("Welcome Home!").subtitleColor(ChatColor.GOLD).fadeIn(15).stay(40).fadeOut(25).ticks().build();
	private static HomePlayerManager users = AdventureHomes.getInstance().getUserManager();

	private ItemMenu returnMenu = null;

	public HomeMenu(HomePlayer player) {
		super(String.format("Your Homes, %s!", player.getName()), Menus.getRows(player.getHomeCount()));
		addItems(player.getHomes());
	}

	public HomeMenu returnAlternative(ItemMenu menu) {
		returnMenu = menu;
		return this;
	}

	public void addItems(List<Warp> warps) {
		int i = 0;
		for (Warp warp : warps) {
			addMenuItem(new SubMenuItem(String.format("&e%s", warp.getName()), new HomeActionMenu(warp)), i++);
		}
	}

	public enum HomeSelectAction {
		DELETE,
		TELEPORT
	}

	public class HomeActionMenu extends ItemMenu {

		public HomeActionMenu(Warp warp) {
			super("Choose an Option!", 1);
			addMenuItem(new HomeActionItem(HomeSelectAction.TELEPORT, warp), 0);
			addMenuItem(new HomeActionItem(HomeSelectAction.DELETE, warp), 8);
		}
	}

	public class HomeActionItem extends MenuItem {
		private HomeSelectAction action;
		private Warp warp;

		public HomeActionItem(HomeSelectAction action, Warp warp) {
			if (action == HomeSelectAction.DELETE) {
				setText(String.format("&cDelete Home '&e%s&c'", warp.getName()));
				setIcon(Wool.RED_WOOL);
			} else {
				setText(String.format("&aGo to Home '&e%s&a'", warp.getName()));
				setIcon(Items.getMaterialData(Material.COMPASS, 0));
			}

			this.action = action;
			this.warp = warp;
		}

		@Override
		public void onClick(Player player, ClickType type) {
			HomePlayer user = users.getUser(player);

			switch (action) {
				case DELETE:
					switchMenu(player, new HomeDeletionConfirmationMenu(warp));
					break;
				case TELEPORT:
					close(player);
					warp.bring(player);
					WELCOME_HOME_TITLE.send(player);
					break;
				default:
					break;
			}
		}
	}

	public class HomeDeletionConfirmationMenu extends ItemMenu {

		private Warp home;

		public HomeDeletionConfirmationMenu(Warp warp) {
			super("Are you Sure?", 1);

			this.home = warp;

			addMenuItem(new HomeDeletionConfirmationItem(DeletionOption.CONFIRM,home),0);
			addMenuItem(new HomeDeletionConfirmationItem(DeletionOption.DENY,home),8);
		}
	}

	public enum DeletionOption {
		CONFIRM,
		DENY
	}

	public class HomeDeletionConfirmationItem extends MenuItem {

		private DeletionOption option;

		private Warp warp;

		public HomeDeletionConfirmationItem(DeletionOption option, Warp warp) {
			this.option = option;

			this.warp = warp;

			if (option == DeletionOption.CONFIRM) {
				setIcon(Wool.GREEN_WOOL);
				setText("&aYes. Delete this home.");
			} else {
				setIcon(Wool.RED_WOOL);
				setText("&cNo. Do not delete this home.");
			}
		}

		@Override
		public void onClick(Player player, ClickType type) {
			HomePlayer user = users.getUser(player);

			switch (option) {
				case CONFIRM:
					PlayerHomeAction action = user.deleteHome(warp);
					switch (action) {
						case HOME_DELETED:
							Chat.message(player, HomeMessages.homeDeleted(warp));
							returnMenus(user);
							break;
						case ERROR:
						case FAILED_TO_DELETE:
							String failedDeleteMessage = HomeMessages.homeDeletedError(warp);
							Chat.messageOps(Messages.playerError(player, failedDeleteMessage));
							Chat.message(player, HomeMessages.homeDeletedError(warp));
							returnMenus(user);
							break;
						case HOME_NONEXISTANT:
							Chat.message(player, HomeMessages.homeNonExistant(warp));
							returnMenus(user);
							break;
						case NO_HOMES_AVAILABLE:
							Chat.message(player, HomeMessages.NO_HOMES_SET);
							returnMenus(user);
							break;
						default:
							break;
					}
					break;
				case DENY:
					Chat.message(player,"&aYour home remains untouched!");
					returnMenus(user);
					break;
			}
		}

		public void returnMenus(HomePlayer user) {
			Player player = user.getPlayer();
			/*
                    If the return menu is set to null, that means the menu wasn't
                    generated from a host plugin / host menu (like the player utility gadget)
                    in the adventure core.

                    If it is, then we'll switch back to the menu specified
                     */
			if (HomeMenu.this.returnMenu == null) {
				if (!user.hasHome()) {
					closeMenu(player);
					Chat.message(player, "&aSet a home using &e/sethome <name>");
					return;
				}
				getMenu().switchMenu(player, new HomeMenu(user));
			} else {
				getMenu().switchMenu(player, returnMenu);
			}
		}
	}
}
