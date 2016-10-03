package com.kanoonsantikul.elysium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer{
    World world;
    SpriteBatch batcher;

    public Renderer(World world, SpriteBatch batcher){
        this.world = world;
        this.batcher = batcher;
    }

    public void render(){
        GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        renderBoard();
        renderCharacter();
        batcher.end();
    }

    private void renderBoard(){
        Tile[] tiles = world.getTiles();
        Tile tile;
        for(int i=0; i<(World.BOARD_SIZE * World.BOARD_SIZE); i++){
            tile = tiles[i];
            batcher.draw(Assets.tile,
                    tile.getPosition().x,
                    tile.getPosition().y,
                    Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    private void renderCharacter(){
        Character character = world.getCharacter();
        batcher.draw(Assets.character,
                character.getPosition().x,
                character.getPosition().y,
                Character.WIDTH,
                Character.HEIGHT);
    }
}
