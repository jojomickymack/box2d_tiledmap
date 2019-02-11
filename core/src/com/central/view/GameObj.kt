package com.central.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.central.lib.ObjectParser
import ktx.box2d.createWorld

object GameObj {
    val stg = Stage()
    val map = TmxMapLoader().load("map01.tmx")
    val mr = OrthogonalTiledMapRenderer(map, 1/32f)
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

    val world = createWorld(gravity = Vector2(0f, -20f))
    val op = ObjectParser()

    init {
        //cam.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }
}