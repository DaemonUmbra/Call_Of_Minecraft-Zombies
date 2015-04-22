package com.zombies.Guns;

import org.bukkit.Material;

import com.zombies.COMZombies;

public class GunType
{
    public COMZombies plugin;
	public String name;
	public GunTypeEnum type;
	public int damage;
	public int fireDelay;
	public int clipammo;
	public int totalammo;
	public int packAPunchClipAmmo;
	public int packAPunchTotalAmmo;
	public int packAPunchDamage;
	public String packAPunchName;

	public GunType(GunTypeEnum type, String gunName, int damage, int fireDelay, int clipammo, int totalammo, int packClip, int packTotal, int packDamage, String packName)
	{
		this.damage = damage;
		this.fireDelay = fireDelay;
		this.clipammo = clipammo;
		this.totalammo = totalammo;
		this.name = gunName;
		this.type = type;
		this.packAPunchClipAmmo = packClip;
		this.packAPunchTotalAmmo = packTotal;
		this.packAPunchDamage = packDamage;
		this.packAPunchName = packName;
	}

	public void updateAmmo(int clip, int total)
	{
		clipammo = clip;
		totalammo = total;
	}

	/**
	 * Used to select the correct material when changing a gun.
	 * 
	 * @return: Material corresponding to the guns type
	 */
	public Material categorizeGun()
	{
		if (type.equals(GunTypeEnum.Pistols)) { return (Material.WOOD_HOE); }
		if (type.equals(GunTypeEnum.Shotguns)) { return Material.STONE_HOE; }
		if (type.equals(GunTypeEnum.AssaultRifles)) { return Material.GOLD_HOE; }
		if (type.equals(GunTypeEnum.LightMachineGuns)) { return Material.IRON_HOE; }
		if (type.equals(GunTypeEnum.SniperRifles)) { return Material.BLAZE_ROD; }
		if (type.equals(GunTypeEnum.SubMachineGuns)) { return Material.STICK; }
		return Material.DIAMOND_HOE;
	}
}
