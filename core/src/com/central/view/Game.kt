package com.central.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.central.App
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.app.KtxScreen
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.central.lib.ObjectParser
import ktx.app.KtxInputAdapter
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.BodyDef


class Player(world: World) {

    var pos = Vector2(50f, 5f)
    val bodyDef = BodyDef()
    var body = world.createBody(bodyDef)

    init {

        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(pos)

        // add it to the world
        body = world.createBody(bodyDef)

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = PolygonShape()
        shape.setAsBox(1f, 1f)

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        // create the physical object in our body)
        // without this our body would just be data in the world
        body.createFixture(shape, 0.0f)

        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }
}

object GameStage {
    val stg = Stage()
    val map = TmxMapLoader().load("map01.tmx")
    val mr = OrthogonalTiledMapRenderer(map, 1/64f)
    val sr = ShapeRenderer()
    val debugRenderer = Box2DDebugRenderer()
    val cam = OrthographicCamera(20f, 20 * (Gdx.graphics.width / Gdx.graphics.height).toFloat())
    val matrix = cam.combined
    val batch = mr.batch

    val floors1 = map.layers.get("floors01") as MapLayer
    val myFloors1 = floors1.objects

    val walls1 = map.layers.get("walls01") as MapLayer
    val myWalls1 = walls1.objects

    val shapeLayer1 = map.layers.get("shapes01") as MapLayer
    val myShapes1 = shapeLayer1.objects

    val shapeLayer2 = map.layers.get("shapes02") as MapLayer
    val myShapes2 = shapeLayer2.objects

    val world = World(Vector2(0f, -20f), true)
    val op = ObjectParser()

    init {
        //cam.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }
}

class Game(val application: App) : KtxScreen {
    val player = Player(GameStage.world)

    init {
        GameStage.cam.update()
        GameStage.sr.projectionMatrix = GameStage.cam.combined

        GameStage.op.parseLayer(GameStage.world, GameStage.myWalls1)
        GameStage.op.parseLayer(GameStage.world, GameStage.myFloors1)
        GameStage.op.parseLayer(GameStage.world, GameStage.myShapes1)
    }

    override fun render(delta: Float) {
        GameStage.world.step(delta, 3, 3)

        GameStage.cam.position.x = player.body.position.x
        GameStage.cam.position.y = player.body.position.y
        GameStage.cam.update()

        GameStage.mr.setView(GameStage.cam)
        GameStage.mr.render()
        GameStage.debugRenderer.render(GameStage.world, GameStage.cam.combined)
    }

    private val inputProcessor = object : KtxInputAdapter {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            val x = screenX / Gdx.graphics.width.toFloat()
            if (x > 0.5f && x < 1f ) { player.body.linearVelocity = Vector2(player.body.linearVelocity.x, 10f) }
            if (x > 0f && x < 0.25f) {
                player.body.linearVelocity = Vector2(-10f, player.body.linearVelocity.y)
            } else if (x > 0.25f && x < 0.5f) {
                player.body.linearVelocity = Vector2(10f, player.body.linearVelocity.y)
            }
            return false
        }
        override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            val x = screenX / Gdx.graphics.width.toFloat()
            when {
                x > 0.5f && x < 1f -> {

                }
                x > 0f && x < 0.25f -> {

                }
                x > 0.25f && x < 0.5f -> {

                }
            }
            return false
        }

        override fun keyDown(keycode: Int): Boolean {
            if (keycode == Keys.UP) {
                player.body.linearVelocity = Vector2(player.body.linearVelocity.x, 10f)
            }
            if (keycode == Keys.DOWN) {
                player.body.linearVelocity = Vector2(player.body.linearVelocity.x, -10f)
            }
            if (keycode == Keys.LEFT || keycode == Keys.A) {
                player.body.linearVelocity = Vector2(-10f, player.body.linearVelocity.y)
            } else if (keycode == Keys.RIGHT || keycode == Keys.D) {
                player.body.linearVelocity = Vector2(10f, player.body.linearVelocity.y)
            }
            return false
        }

        override fun keyUp(keycode: Int): Boolean {
            if (keycode == Keys.SPACE) {
                player.body.linearVelocity = Vector2(player.body.linearVelocity.x, player.body.linearVelocity.y)
            }
            if (keycode == Keys.LEFT || keycode == Keys.A) {
                player.body.linearVelocity = Vector2(player.body.linearVelocity.x, player.body.linearVelocity.y)
            }
            if (keycode == Keys.RIGHT || keycode == Keys.D) {
                player.body.linearVelocity = Vector2(player.body.linearVelocity.x, player.body.linearVelocity.y)
            }
            return false
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(inputProcessor, GameStage.stg)
    }
}
