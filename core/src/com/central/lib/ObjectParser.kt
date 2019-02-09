package com.central.lib

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.objects.PolylineMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*


class ObjectParser {
    private fun rectToPoly(rect: Rectangle): Polygon {
        return Polygon(floatArrayOf(rect.x, rect.y, rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height, rect.x + rect.width, rect.y))
    }

    fun parseLayer(world: World, objects: MapObjects) {
        objects.forEach {
            when (it) {
                is PolylineMapObject -> {
                    val vertices = it.polyline.transformedVertices
                    val worldVertices = arrayOfNulls<Vector2>(vertices.size / 2)

                    for (i in worldVertices.indices) {
                        worldVertices[i] = Vector2(vertices[i * 2] / 32, vertices[i * 2 + 1] / 32)
                    }
                    val chainShape = ChainShape()
                    chainShape.createChain(worldVertices)

                    val bdef = BodyDef()
                    bdef.type = BodyDef.BodyType.StaticBody
                    val body = world.createBody(bdef)
                    body.createFixture(chainShape, 1.0f)
                    chainShape.dispose()
                }
                is PolygonMapObject -> {
                    val vertices = it.polygon.transformedVertices
                    val worldVertices = arrayOfNulls<Vector2>(vertices.size / 2)

                    for (i in worldVertices.indices) {
                        worldVertices[i] = Vector2(vertices[i * 2] / 32, vertices[i * 2 + 1] / 32)
                    }
                    val polygonShape = PolygonShape()
                    polygonShape.set(worldVertices)

                    val bdef = BodyDef()
                    bdef.type = BodyDef.BodyType.StaticBody
                    val body = world.createBody(bdef)
                    body.createFixture(polygonShape, 1.0f)
                    polygonShape.dispose()
                }
                is RectangleMapObject -> {
                    val vertices = rectToPoly(it.rectangle).transformedVertices
                    val worldVertices = arrayOfNulls<Vector2>(vertices.size / 2)

                    for (i in worldVertices.indices) {
                        worldVertices[i] = Vector2(vertices[i * 2] / 32, vertices[i * 2 + 1] / 32)
                    }
                    val polygonShape = PolygonShape()
                    polygonShape.set(worldVertices)

                    val bdef = BodyDef()
                    bdef.type = BodyDef.BodyType.StaticBody
                    val body = world.createBody(bdef)
                    body.createFixture(polygonShape, 1.0f)
                    polygonShape.dispose()
                }
            }
        }
    }
}