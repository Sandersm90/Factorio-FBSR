package demod.fbsr.render;

import java.util.function.Consumer;

import demod.fbsr.BlueprintEntity;
import demod.fbsr.BlueprintEntity.Direction;
import demod.fbsr.DataPrototype;
import demod.fbsr.DataTable;
import demod.fbsr.WorldMap;

public class PipeRendering extends TypeRendererFactory {

	public static final String[] pipeSpriteNameMapping = //
			new String[/* bits WSEN */] { //
					"straight_horizontal", // ....
					"ending_up", // ...N
					"ending_right", // ..E.
					"corner_up_right", // ..EN
					"ending_down", // .S..
					"straight_vertical", // .S.N
					"corner_down_right", // .SE.
					"t_right", // .SEN
					"ending_left", // W...
					"corner_up_left", // W..N
					"straight_horizontal", // W.E.
					"t_up", // W.EN
					"corner_down_left", // WS..
					"t_left", // WS.N
					"t_down", // WSE.
					"cross",// WSEN
			};

	@Override
	public void createRenderers(Consumer<Renderer> register, WorldMap map, DataTable dataTable, BlueprintEntity entity,
			DataPrototype prototype) {
		int adjCode = 0;
		adjCode |= ((pipeFacingMeFrom(Direction.NORTH, map, entity) ? 1 : 0) << 0);
		adjCode |= ((pipeFacingMeFrom(Direction.EAST, map, entity) ? 1 : 0) << 1);
		adjCode |= ((pipeFacingMeFrom(Direction.SOUTH, map, entity) ? 1 : 0) << 2);
		adjCode |= ((pipeFacingMeFrom(Direction.WEST, map, entity) ? 1 : 0) << 3);
		String spriteName = pipeSpriteNameMapping[adjCode];

		Sprite sprite = getSpriteFromAnimation(prototype.lua().get("pictures").get(spriteName));

		register.accept(spriteRenderer(sprite, entity, prototype));
	}

	public boolean pipeFacingMeFrom(Direction direction, WorldMap map, BlueprintEntity entity) {
		return map.isPipe(direction.offset(entity.getPosition()), direction.back());
	}

	@Override
	public void populateWorldMap(WorldMap map, DataTable dataTable, BlueprintEntity entity, DataPrototype prototype) {
		map.setPipe(entity.getPosition());
	}

}
