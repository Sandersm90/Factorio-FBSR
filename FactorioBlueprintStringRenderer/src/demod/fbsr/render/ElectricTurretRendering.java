package demod.fbsr.render;

import java.util.function.Consumer;

import org.luaj.vm2.LuaValue;

import demod.fbsr.BlueprintEntity;
import demod.fbsr.DataPrototype;
import demod.fbsr.DataTable;
import demod.fbsr.WorldMap;

public class ElectricTurretRendering extends TypeRendererFactory {
	@Override
	public void createRenderers(Consumer<Renderer> register, WorldMap map, DataTable dataTable, BlueprintEntity entity,
			DataPrototype prototype) {
		Sprite baseSprite = getSpriteFromAnimation(prototype.lua().get("base_picture").get("layers").get(1));
		register.accept(spriteRenderer(baseSprite, entity, prototype));
		LuaValue turretLayers = prototype.lua().get("folded_animation").get("layers");
		Sprite turretSprite = getSpriteFromAnimation(turretLayers.get(1));
		turretSprite.source.y += turretSprite.source.height * entity.getDirection().cardinal();
		register.accept(spriteRenderer(turretSprite, entity, prototype));
	}
}
