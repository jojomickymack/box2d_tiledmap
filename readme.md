# LibGdx/Ktx Example Using Box2d and Tile Map Polyline Collision

![box2d example](.github/box2d.gif?raw=true)

This example loads a tiled map which contains some object layers (floors, walls, shapes) that are made of polygon and 
polylines objects. The objects are converted into box2d shapes.

Box2d can easily render the shapes in the world by calling debugRender, and the box2d map can be rendered just by calling 
render(). These end up sharing the same coordinate system because they share the same camera.

The 'player' is just a dynamic body in the world whose linearVelocity is bound to key input. On mobile, the left and right 
sides of the screen act as a controller (this is taken from the superkoalio example).

If you are trying to get arcade style physics for a 2d platformer game, you probably don't want to use box2d. I read an article at some point that explained how box2d is 'too realistic' for those types of games, and I agree. If you want to knock things over and see them scatter realistically, box2d is perfect - if you want to have precise control over a characteer jumping around and stopping when touching a wall, you're not going to like box2d.
