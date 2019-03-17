package com.central.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputMultiplexer
import com.central.App
import ktx.app.KtxScreen
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxInputAdapter
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody
import ktx.box2d.body


class Player(world: World) {

    var body = world.body {
        type = DynamicBody
        position.set(Vector2(50f, 5f))
        box(width = 2f, height = 2f) {
            density = 1f
        }
    }
}

class Game(val application: App) : KtxScreen {
    val player = Player(GameObj.world)

    init {
        with(GameObj) {
            cam.update()
            sr.projectionMatrix = cam.combined
            op.parseLayer(world, myWalls1)
            op.parseLayer(world, myFloors1)
            op.parseLayer(world, myShapes1)
        }
    }

    override fun render(delta: Float) {
        with(GameObj) {
            world.step(delta, 3, 3)
            cam.position.x = player.body.position.x
            cam.position.y = player.body.position.y
            cam.update()
            mr.setView(cam)
            mr.render()
            debugRenderer.render(world, cam.combined)
        }
    }

    private val inputProcessor = object : KtxInputAdapter {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            with(player.body) {
                val x = screenX / Gdx.graphics.width.toFloat()
                if (x > 0.5f && x < 1f) {
                    linearVelocity = Vector2(linearVelocity.x, 10f)
                }
                if (x > 0f && x < 0.25f) {
                    linearVelocity = Vector2(-10f, linearVelocity.y)
                } else if (x > 0.25f && x < 0.5f) {
                    linearVelocity = Vector2(10f, linearVelocity.y)
                }
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
            with(player.body) {
                if (keycode == Keys.UP) {
                    linearVelocity = Vector2(linearVelocity.x, 10f)
                }
                if (keycode == Keys.DOWN) {
                    linearVelocity = Vector2(linearVelocity.x, -10f)
                }
                if (keycode == Keys.LEFT || keycode == Keys.A) {
                    linearVelocity = Vector2(-10f, linearVelocity.y)
                } else if (keycode == Keys.RIGHT || keycode == Keys.D) {
                    linearVelocity = Vector2(10f, linearVelocity.y)
                }
            }
            return false
        }

        override fun keyUp(keycode: Int): Boolean {
            with(player.body) {
                if (keycode == Keys.SPACE) {
                    linearVelocity = Vector2(player.body.linearVelocity.x, player.body.linearVelocity.y)
                }
                if (keycode == Keys.LEFT || keycode == Keys.A) {
                    linearVelocity = Vector2(linearVelocity.x, player.body.linearVelocity.y)
                }
                if (keycode == Keys.RIGHT || keycode == Keys.D) {
                    linearVelocity = Vector2(linearVelocity.x, player.body.linearVelocity.y)
                }
            }
            return false
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(inputProcessor, GameObj.stg)
    }

    override fun dispose() {
        GameObj.dispose()
        println("all disposable memory freed")
        super.dispose()
    }
}
