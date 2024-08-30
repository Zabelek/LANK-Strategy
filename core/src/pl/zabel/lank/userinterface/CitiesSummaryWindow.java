package pl.zabel.lank.userinterface;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.GameplayDataReport;
import pl.zabel.lank.utilities.LanguageMap;

public class CitiesSummaryWindow extends MenuWindow {

	private Label citiesCountLabel, titaniumGainLabel, petroleumGainLabel, uraniumGainLabel, goldGainLabel,
			speedBonusLabel, repairBonusLabel, attackSpeedBotLabel, tankDamageBonusLabel, regenBonusLabel,
			armorBonusLabel, shellSpeedBonusLabel, tankRangeLabel, cunitBuildSpeedLabel, cunitRepairLabel,
			cunitStorageLabel, cunitAttackSpeedLabel, cunitSpeeedLabel, cunitRegenLabel, cunitDamageLabel,
			cunitRangeLabel, cunitHpLabel, soAttackBonusLabel, soDeffenceBonusLabel, soCyborgFlairBonusLabel,
			soMutantFlairBonusLabel;
	private SiegeReportsWindow siegeReportsWindow;

	public CitiesSummaryWindow() {
		super(LanguageMap.findString("citySummaryTitle"), 500 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY(),
				920 * GameVariables.getScaleX(), 780 * GameVariables.getScaleY());
		this.textDisplayed.setY(390 * GameVariables.getScaleY());
		// general
		Label generalSummarySign = new Label(LanguageMap.findString("citySummaryAll"),
				TextStylesContainer.smallTextStyle);
		generalSummarySign.setBounds(80 * GameVariables.getScaleX(), 650 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(generalSummarySign);
		Label citiesConuntSign = new Label(LanguageMap.findString("citySummaryCityCount"),
				TextStylesContainer.smallTextStyle);
		Label titaniumGainSign = new Label(LanguageMap.findString("citySummaryTitaniumOnTurn"),
				TextStylesContainer.smallTextStyle);
		Label petroleumGainSign = new Label(LanguageMap.findString("citySummaryPetroleumOnTurn"),
				TextStylesContainer.smallTextStyle);
		Label uraniumGainSign = new Label(LanguageMap.findString("citySummaryUraniumOnTurn"),
				TextStylesContainer.smallTextStyle);
		Label goldGainSign = new Label(LanguageMap.findString("citySummaryGoldOnTurn"),
				TextStylesContainer.smallTextStyle);
		citiesConuntSign.setBounds(50 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		titaniumGainSign.setBounds(50 * GameVariables.getScaleX(), 570 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		petroleumGainSign.setBounds(50 * GameVariables.getScaleX(), 540 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		uraniumGainSign.setBounds(50 * GameVariables.getScaleX(), 510 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		goldGainSign.setBounds(50 * GameVariables.getScaleX(), 480 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(citiesConuntSign);
		this.addActor(titaniumGainSign);
		this.addActor(petroleumGainSign);
		this.addActor(uraniumGainSign);
		this.addActor(goldGainSign);
		this.citiesCountLabel = new Label("0", TextStylesContainer.smallTextStyle);
		this.titaniumGainLabel = new Label("0", TextStylesContainer.smallTextStyle);
		this.petroleumGainLabel = new Label("0", TextStylesContainer.smallTextStyle);
		this.uraniumGainLabel = new Label("0", TextStylesContainer.smallTextStyle);
		this.goldGainLabel = new Label("0", TextStylesContainer.smallTextStyle);
		this.citiesCountLabel.setBounds(400 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.titaniumGainLabel.setBounds(400 * GameVariables.getScaleX(), 570 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.petroleumGainLabel.setBounds(400 * GameVariables.getScaleX(), 540 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.uraniumGainLabel.setBounds(400 * GameVariables.getScaleX(), 510 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.goldGainLabel.setBounds(400 * GameVariables.getScaleX(), 480 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(citiesCountLabel);
		this.addActor(titaniumGainLabel);
		this.addActor(petroleumGainLabel);
		this.addActor(uraniumGainLabel);
		this.addActor(goldGainLabel);
		// normal units:
		Label unitsSummarySign = new Label(LanguageMap.findString("citySummaryNormalUnitLabel"),
				TextStylesContainer.smallTextStyle);
		unitsSummarySign.setBounds(80 * GameVariables.getScaleX(), 430 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(unitsSummarySign);
		Label speedBonusSign = new Label(LanguageMap.findString("citySummaryUnitSpeed"),
				TextStylesContainer.smallTextStyle);
		Label repairBonusSign = new Label(LanguageMap.findString("citySummaryUnitRepairSpeed"),
				TextStylesContainer.smallTextStyle);
		Label attackSpeedBotSign = new Label(LanguageMap.findString("citySummaryUnitShootSpeed"),
				TextStylesContainer.smallTextStyle);
		Label tankDamageBonusSign = new Label(LanguageMap.findString("citySummaryTankDamage"),
				TextStylesContainer.smallTextStyle);
		Label regenBonusSign = new Label(LanguageMap.findString("citySummaryUnitArmorRegen"),
				TextStylesContainer.smallTextStyle);
		Label armorBonusSign = new Label(LanguageMap.findString("citySummaryUnitArmor"),
				TextStylesContainer.smallTextStyle);
		Label shellSpeedBonusSign = new Label(LanguageMap.findString("citySummaryBotBulletSpeed"),
				TextStylesContainer.smallTextStyle);
		Label tankRangeSign = new Label(LanguageMap.findString("citySummaryTankRange"),
				TextStylesContainer.smallTextStyle);
		speedBonusSign.setBounds(50 * GameVariables.getScaleX(), 380 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		repairBonusSign.setBounds(50 * GameVariables.getScaleX(), 350 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		attackSpeedBotSign.setBounds(50 * GameVariables.getScaleX(), 320 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		tankDamageBonusSign.setBounds(50 * GameVariables.getScaleX(), 290 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		regenBonusSign.setBounds(50 * GameVariables.getScaleX(), 260 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		armorBonusSign.setBounds(50 * GameVariables.getScaleX(), 230 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		shellSpeedBonusSign.setBounds(50 * GameVariables.getScaleX(), 200 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		tankRangeSign.setBounds(50 * GameVariables.getScaleX(), 170 * GameVariables.getScaleY(), 180 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(speedBonusSign);
		this.addActor(repairBonusSign);
		this.addActor(attackSpeedBotSign);
		this.addActor(tankDamageBonusSign);
		this.addActor(regenBonusSign);
		this.addActor(armorBonusSign);
		this.addActor(shellSpeedBonusSign);
		this.addActor(tankRangeSign);
		this.speedBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.repairBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.attackSpeedBotLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.tankDamageBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.regenBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.armorBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.shellSpeedBonusLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.tankRangeLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		this.speedBonusLabel.setBounds(400 * GameVariables.getScaleX(), 380 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.repairBonusLabel.setBounds(400 * GameVariables.getScaleX(), 350 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.attackSpeedBotLabel.setBounds(400 * GameVariables.getScaleX(), 320 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.tankDamageBonusLabel.setBounds(400 * GameVariables.getScaleX(), 290 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.regenBonusLabel.setBounds(400 * GameVariables.getScaleX(), 260 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.armorBonusLabel.setBounds(400 * GameVariables.getScaleX(), 230 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.shellSpeedBonusLabel.setBounds(400 * GameVariables.getScaleX(), 200 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.tankRangeLabel.setBounds(400 * GameVariables.getScaleX(), 170 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.addActor(speedBonusLabel);
		this.addActor(repairBonusLabel);
		this.addActor(attackSpeedBotLabel);
		this.addActor(tankDamageBonusLabel);
		this.addActor(regenBonusLabel);
		this.addActor(armorBonusLabel);
		this.addActor(shellSpeedBonusLabel);
		this.addActor(tankRangeLabel);
		// command unit:
		Label commandUnitSummarySign = new Label(LanguageMap.findString("citySummaryCulabel"),
				TextStylesContainer.smallTextStyle);
		commandUnitSummarySign.setBounds(520 * GameVariables.getScaleX(), 650 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.addActor(commandUnitSummarySign);
		Label cunitBuildSpeedSign = new Label(LanguageMap.findString("citySummaryCuBuildSpeed"),
				TextStylesContainer.smallTextStyle);
		Label cunitRepairSign = new Label(LanguageMap.findString("citySummaryCuRepairSpeed"),
				TextStylesContainer.smallTextStyle);
		Label cunitStorageSign = new Label(LanguageMap.findString("citySummaryCuStorage"),
				TextStylesContainer.smallTextStyle);
		Label cunitAttackSpeedSign = new Label(LanguageMap.findString("citySummaryCuShootSpeed"),
				TextStylesContainer.smallTextStyle);
		Label cunitSpeeedSign = new Label(LanguageMap.findString("citySummaryCuSpeed"),
				TextStylesContainer.smallTextStyle);
		Label cunitRegenSign = new Label(LanguageMap.findString("citySummaryCuRegen"),
				TextStylesContainer.smallTextStyle);
		Label cunitDamageSign = new Label(LanguageMap.findString("citySummaryCuDamage"),
				TextStylesContainer.smallTextStyle);
		Label cunitRangeSign = new Label(LanguageMap.findString("citySummaryCuRange"),
				TextStylesContainer.smallTextStyle);
		Label cunitHpSign = new Label(LanguageMap.findString("citySummaryCuArmor"), TextStylesContainer.smallTextStyle);
		cunitBuildSpeedSign.setBounds(490 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		cunitRepairSign.setBounds(490 * GameVariables.getScaleX(), 570 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitStorageSign.setBounds(490 * GameVariables.getScaleX(), 540 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitAttackSpeedSign.setBounds(490 * GameVariables.getScaleX(), 510 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		cunitSpeeedSign.setBounds(490 * GameVariables.getScaleX(), 480 * GameVariables.getScaleY(), 260 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitRegenSign.setBounds(490 * GameVariables.getScaleX(), 450 * GameVariables.getScaleY(), 230 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitDamageSign.setBounds(490 * GameVariables.getScaleX(), 420 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitRangeSign.setBounds(490 * GameVariables.getScaleX(), 390 * GameVariables.getScaleY(), 180 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitHpSign.setBounds(490 * GameVariables.getScaleX(), 360 * GameVariables.getScaleY(), 180 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(cunitBuildSpeedSign);
		this.addActor(cunitRepairSign);
		this.addActor(cunitStorageSign);
		this.addActor(cunitAttackSpeedSign);
		this.addActor(cunitSpeeedSign);
		this.addActor(cunitRegenSign);
		this.addActor(cunitDamageSign);
		this.addActor(cunitRangeSign);
		this.addActor(cunitHpSign);
		cunitBuildSpeedLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitRepairLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitStorageLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitAttackSpeedLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitSpeeedLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitRegenLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitDamageLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitRangeLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitHpLabel = new Label("0%", TextStylesContainer.smallTextStyle);
		cunitBuildSpeedLabel.setBounds(840 * GameVariables.getScaleX(), 600 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		cunitRepairLabel.setBounds(840 * GameVariables.getScaleX(), 570 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitStorageLabel.setBounds(840 * GameVariables.getScaleX(), 540 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitAttackSpeedLabel.setBounds(840 * GameVariables.getScaleX(), 510 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		cunitSpeeedLabel.setBounds(840 * GameVariables.getScaleX(), 480 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitRegenLabel.setBounds(840 * GameVariables.getScaleX(), 450 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitDamageLabel.setBounds(840 * GameVariables.getScaleX(), 420 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitRangeLabel.setBounds(840 * GameVariables.getScaleX(), 390 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		cunitHpLabel.setBounds(840 * GameVariables.getScaleX(), 360 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		this.addActor(cunitBuildSpeedLabel);
		this.addActor(cunitRepairLabel);
		this.addActor(cunitStorageLabel);
		this.addActor(cunitAttackSpeedLabel);
		this.addActor(cunitSpeeedLabel);
		this.addActor(cunitRegenLabel);
		this.addActor(cunitDamageLabel);
		this.addActor(cunitRangeLabel);
		this.addActor(cunitHpLabel);

		// support officers
		Label supportOfficersSummarySign = new Label(LanguageMap.findString("citySummaryOfficersLabel"),
				TextStylesContainer.smallTextStyle);
		supportOfficersSummarySign.setBounds(520 * GameVariables.getScaleX(), 310 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.addActor(supportOfficersSummarySign);
		Label soAttackBonusSign = new Label(LanguageMap.findString("citySummaryOfAdAttack"),
				TextStylesContainer.smallTextStyle);
		Label soDeffenceBonusSign = new Label(LanguageMap.findString("citySummaryOfAdArmor"),
				TextStylesContainer.smallTextStyle);
		Label soCyborgFlairBonusSign = new Label(LanguageMap.findString("citySummaryOdCybFlair"),
				TextStylesContainer.smallTextStyle);
		Label soMutantFlairBonusSign = new Label(LanguageMap.findString("citySummaryOfMutFlair"),
				TextStylesContainer.smallTextStyle);
		soAttackBonusSign.setBounds(490 * GameVariables.getScaleX(), 260 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		soDeffenceBonusSign.setBounds(490 * GameVariables.getScaleX(), 230 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		soCyborgFlairBonusSign.setBounds(490 * GameVariables.getScaleX(), 200 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		soMutantFlairBonusSign.setBounds(490 * GameVariables.getScaleX(), 170 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.addActor(soAttackBonusSign);
		this.addActor(soDeffenceBonusSign);
		this.addActor(soCyborgFlairBonusSign);
		this.addActor(soMutantFlairBonusSign);
		soAttackBonusLabel = new Label("0", TextStylesContainer.smallTextStyle);
		soDeffenceBonusLabel = new Label("0", TextStylesContainer.smallTextStyle);
		soCyborgFlairBonusLabel = new Label("0", TextStylesContainer.smallTextStyle);
		soMutantFlairBonusLabel = new Label("0", TextStylesContainer.smallTextStyle);
		soAttackBonusLabel.setBounds(840 * GameVariables.getScaleX(), 260 * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
				60 * GameVariables.getScaleY());
		soDeffenceBonusLabel.setBounds(840 * GameVariables.getScaleX(), 230 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		soCyborgFlairBonusLabel.setBounds(840 * GameVariables.getScaleX(), 200 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		soMutantFlairBonusLabel.setBounds(840 * GameVariables.getScaleX(), 170 * GameVariables.getScaleY(),
				150 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY());
		this.addActor(soAttackBonusLabel);
		this.addActor(soDeffenceBonusLabel);
		this.addActor(soCyborgFlairBonusLabel);
		this.addActor(soMutantFlairBonusLabel);
		// siegeReports
		MenuButton enemySignReportsButton = new MenuButton(LanguageMap.findString("citySummarySiegeReportLabel"),
				100 * GameVariables.getScaleX(), this.okButton.getY());
		enemySignReportsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CitiesSummaryWindow.this.setVisible(false);
				siegeReportsWindow.setVisible(true);
			}
		});
		this.addActor(enemySignReportsButton);
		this.siegeReportsWindow = new SiegeReportsWindow();
		this.siegeReportsWindow.setVisible(false);
		this.okButton.setPosition(600 * GameVariables.getScaleX(), this.okButton.getY());
	}

	public void updateSummary() {
		GameplayDataReport report = GameSession.generateGameplayDataReport();
		// resources
		this.citiesCountLabel.setText(report.citiesAmount);
		this.titaniumGainLabel.setText(report.titaniumGain);
		this.petroleumGainLabel.setText(report.petroleumGain);
		this.uraniumGainLabel.setText(report.uraniumGain);
		this.goldGainLabel.setText(report.goldGain);

		// normal units
		this.tankDamageBonusLabel.setText((report.tankDamageUpgCount * 10) + "%");
		this.armorBonusLabel.setText((report.armorUpgcount * 10) + "%");
		this.tankRangeLabel.setText((report.tankRangeUpgCount * 10) + "%");
		this.regenBonusLabel.setText((report.regenUpgCount * 10) + "%");
		this.repairBonusLabel.setText((report.repairUpgCount * 10) + "%");
		this.shellSpeedBonusLabel.setText((report.shellSpeedUpgCount * 10) + "%");
		this.attackSpeedBotLabel.setText((report.attackSpeedUpgCount * 10) + "%");
		this.speedBonusLabel.setText((report.speedUpgCount * 10) + "%");

		// command unit labels
		this.cunitBuildSpeedLabel.setText((report.cunitBuildSpeedUpgCount * 10) + "%");
		this.cunitRepairLabel.setText((report.cunitRepairUpgCount * 10) + "%");
		this.cunitRegenLabel.setText((report.cunitRegenUpgCount * 10) + "%");
		this.cunitStorageLabel.setText((report.cunitStorageUpgCount * 10) + "%");
		this.cunitAttackSpeedLabel.setText((report.cunitAttackSpeedUpgCount * 10) + "%");
		this.cunitSpeeedLabel.setText((report.cunitSpeedUpgCount * 10) + "%");
		this.cunitDamageLabel.setText((report.cunitDamageUpgCount * 10) + "%");
		this.cunitRangeLabel.setText((report.cunitRangeUpgCount * 10) + "%");
		this.cunitHpLabel.setText((report.cunitHpUpgCount * 10) + "%");

		// support officers units
		this.soAttackBonusLabel.setText(report.soAttackUpgCount);
		this.soDeffenceBonusLabel.setText(report.soDeffenceUpgCount);
		this.soCyborgFlairBonusLabel.setText(report.soCyborgFlairUpgCount);
		this.soMutantFlairBonusLabel.setText(report.soMutantFlairUpgCount);
	}

	public SiegeReportsWindow getSiegeReportsWindow() {
		return this.siegeReportsWindow;
	}

	public class SiegeReportsWindow extends MenuWindow {
		private Label dataLabel;

		public SiegeReportsWindow() {
			super(LanguageMap.findString("citySummaryAlienAttacksInfoLabel"), 460 * GameVariables.getScaleX(),
					290 * GameVariables.getScaleY(), 1000 * GameVariables.getScaleX(), 500 * GameVariables.getScaleY());
			this.textDisplayed.setY(250 * GameVariables.getScaleY());
			dataLabel = new Label(LanguageMap.findString("siegeReportNoSieges"), TextStylesContainer.smallTextStyle);
			dataLabel.setBounds(30 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY(), 940 * GameVariables.getScaleX(),
					400 * GameVariables.getScaleY());
			dataLabel.setAlignment(Align.center);
			dataLabel.setTouchable(Touchable.disabled);
			this.addActor(dataLabel);
		}

		@Override
		public void setText(String text) {
			this.dataLabel.setText(text);
		}

		public void addText(String text) {
			this.dataLabel.setText(this.dataLabel.getText() + "\n" + text);
		}
	}
}
