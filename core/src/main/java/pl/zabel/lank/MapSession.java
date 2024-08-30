package pl.zabel.lank;

import pl.zabel.lank.utilities.GameplayDataReport;

public class MapSession {
	//eco
	private static float maxMetalAmount, maxEnergyAmount, currentMetalAmount, currentEnergyAmount, 
	currentMetalIncreasePerSec, currentEnergyIncreasePerSec;
	//stats
	private static float unitMovementSpeedMod, unitRepairSpeedMod, unitShootSpeedMod, tankDamageMod,
	unitArmorRegenMod, botBulletSpeedMod, tankRangeMod, cuBuildSpeedMod, cuRepairSpeedMod, cuResourceStorageMod,
	cuShootSpeedMod, cuMovementSpeedMod, cuRegenMod, cuDamageMod, cuRangeMod, cuHpMod;
	
	public static void init()
	{
		maxMetalAmount = 1000;
		maxEnergyAmount = 1000;
		currentMetalAmount = maxMetalAmount;
		currentEnergyAmount = maxEnergyAmount;
		currentEnergyIncreasePerSec = 0;
		
	}
	private static void setUpSessionModifiers() {
		GameplayDataReport report = GameSession.generateGameplayDataReport();
		unitMovementSpeedMod = 1f+(0.1f*report.speedUpgCount);
		unitRepairSpeedMod = 1f+(0.1f*report.repairUpgCount);
		unitShootSpeedMod = 1f+(0.1f*report.attackSpeedUpgCount);
		tankDamageMod = 1f+(0.1f*report.tankDamageUpgCount);
		unitArmorRegenMod = 1f+(0.1f*report.armorUpgcount);
		botBulletSpeedMod = 1f+(0.1f*report.shellSpeedUpgCount);
		tankRangeMod = 1f+(0.1f*report.tankRangeUpgCount);
		cuBuildSpeedMod = 1f+(0.1f*report.cunitBuildSpeedUpgCount);
		cuRepairSpeedMod = 1f+(0.1f*report.cunitRepairUpgCount);
		cuResourceStorageMod = 1f+(0.1f*report.cunitStorageUpgCount);
		cuShootSpeedMod = 1f+(0.1f*report.cunitAttackSpeedUpgCount);
		cuMovementSpeedMod = 1f+(0.1f*report.cunitSpeedUpgCount);
		cuRegenMod = 1f+(0.1f*report.cunitRegenUpgCount);
		cuDamageMod = 1f+(0.1f*report.cunitDamageUpgCount);
		cuRangeMod = 1f+(0.1f*report.cunitRangeUpgCount);
		cuHpMod = 1f+(0.1f*report.cunitHpUpgCount);
	}
	public static void resetMapSession()
	{
		setUpSessionModifiers();
		maxMetalAmount = 1000*cuResourceStorageMod;
		maxEnergyAmount = 1000*cuResourceStorageMod;
		currentMetalAmount = maxMetalAmount;
		currentEnergyAmount = maxEnergyAmount;
		currentMetalIncreasePerSec = 0;
		currentEnergyIncreasePerSec = 0;
	}
	public static void modifyCurrentMetalAmount(float value)
	{
		currentMetalAmount+=value;
		if(currentMetalAmount>maxMetalAmount)
			currentMetalAmount = maxMetalAmount;
		if(currentMetalAmount<0)
			currentMetalAmount=0;
	}
	public static void modifyCurrentEnergyAmount(float value)
	{
		currentEnergyAmount+=value;
		if(currentEnergyAmount>maxEnergyAmount)
			currentEnergyAmount = maxEnergyAmount;
		if(currentEnergyAmount<0)
			currentEnergyAmount=0;
	}
	public static void modifyCurrentMetalIncreasePerSec(float value, float speed)
	{
		currentMetalIncreasePerSec+=(value*speed);
	}
	public static void modifyCurrentEnergyIncreasePerSec(float value, float speed)
	{
		currentEnergyIncreasePerSec+=(value*speed);
	}
	public static float getMaxMetalAmount() {
		return maxMetalAmount;
	}
	public static float getMaxEnergyAmount() {
		return maxEnergyAmount;
	}
	public static float getCurrentMetalAmount() {
		return currentMetalAmount;
	}
	public static float getCurrentEnergyAmount() {
		return currentEnergyAmount;
	}
	public static float getCurrentMetalIncreasePerSec() {
		return currentMetalIncreasePerSec;
	}
	public static float getCurrentEnergyIncreasePerSec() {
		return currentEnergyIncreasePerSec;
	}
	public static float getUnitMovementSpeedMod() {
		return unitMovementSpeedMod;
	}
	public static void setUnitMovementSpeedMod(float unitMovementSpeedMod) {
		MapSession.unitMovementSpeedMod = unitMovementSpeedMod;
	}
	public static float getUnitRepairSpeedMod() {
		return unitRepairSpeedMod;
	}
	public static void setUnitRepairSpeedMod(float unitRepairSpeedMod) {
		MapSession.unitRepairSpeedMod = unitRepairSpeedMod;
	}
	public static float getUnitShootSpeedMod() {
		return unitShootSpeedMod;
	}
	public static void setUnitShootSpeedMod(float unitShootSpeedMod) {
		MapSession.unitShootSpeedMod = unitShootSpeedMod;
	}
	public static float getTankDamageMod() {
		return tankDamageMod;
	}
	public static void setTankDamageMod(float tankDamageMod) {
		MapSession.tankDamageMod = tankDamageMod;
	}
	public static float getUnitArmorRegenMod() {
		return unitArmorRegenMod;
	}
	public static void setUnitArmorRegenMod(float unitArmorRegenMod) {
		MapSession.unitArmorRegenMod = unitArmorRegenMod;
	}
	public static float getBotBulletSpeedMod() {
		return botBulletSpeedMod;
	}
	public static void setBotBulletSpeedMod(float botBulletSpeedMod) {
		MapSession.botBulletSpeedMod = botBulletSpeedMod;
	}
	public static float getTankRangeMod() {
		return tankRangeMod;
	}
	public static void setTankRangeMod(float tankRangeMod) {
		MapSession.tankRangeMod = tankRangeMod;
	}
	public static float getCuBuildSpeedMod() {
		return cuBuildSpeedMod;
	}
	public static void setCuBuildSpeedMod(float cuBuildSpeedMod) {
		MapSession.cuBuildSpeedMod = cuBuildSpeedMod;
	}
	public static float getCuRepairSpeedMod() {
		return cuRepairSpeedMod;
	}
	public static void setCuRepairSpeedMod(float cuRepairSpeedMod) {
		MapSession.cuRepairSpeedMod = cuRepairSpeedMod;
	}
	public static float getCuResourceStorageMod() {
		return cuResourceStorageMod;
	}
	public static void setCuResourceStorageMod(float cuResourceStorageMod) {
		MapSession.cuResourceStorageMod = cuResourceStorageMod;
	}
	public static float getCuShootSpeedMod() {
		return cuShootSpeedMod;
	}
	public static void setCuShootSpeedMod(float cuShootSpeedMod) {
		MapSession.cuShootSpeedMod = cuShootSpeedMod;
	}
	public static float getCuMovementSpeedMod() {
		return cuMovementSpeedMod;
	}
	public static void setCuMovementSpeedMod(float cuMovementSpeedMod) {
		MapSession.cuMovementSpeedMod = cuMovementSpeedMod;
	}
	public static float getCuRegenMod() {
		return cuRegenMod;
	}
	public static void setCuRegenMod(float cuRegenMod) {
		MapSession.cuRegenMod = cuRegenMod;
	}
	public static float getCuDamageMod() {
		return cuDamageMod;
	}
	public static void setCuDamageMod(float cuDamageMod) {
		MapSession.cuDamageMod = cuDamageMod;
	}
	public static float getCuRangeMod() {
		return cuRangeMod;
	}
	public static void setCuRangeMod(float cuRangeMod) {
		MapSession.cuRangeMod = cuRangeMod;
	}
	public static float getCuHpMod() {
		return cuHpMod;
	}
	public static void setCuHpMod(float cuHpMod) {
		MapSession.cuHpMod = cuHpMod;
	}
	public static void setMaxMetalAmount(float maxMetalAmount) {
		MapSession.maxMetalAmount = maxMetalAmount;
	}
	public static void setMaxEnergyAmount(float maxEnergyAmount) {
		MapSession.maxEnergyAmount = maxEnergyAmount;
	}
	public static void setCurrentMetalAmount(float currentMetalAmount) {
		MapSession.currentMetalAmount = currentMetalAmount;
	}
	public static void setCurrentEnergyAmount(float currentEnergyAmount) {
		MapSession.currentEnergyAmount = currentEnergyAmount;
	}
	public static void setCurrentMetalIncreasePerSec(float currentMetalIncreasePerSec) {
		MapSession.currentMetalIncreasePerSec = currentMetalIncreasePerSec;
	}
	public static void setCurrentEnergyIncreasePerSec(float currentEnergyIncreasePerSec) {
		MapSession.currentEnergyIncreasePerSec = currentEnergyIncreasePerSec;
	}
	
}
