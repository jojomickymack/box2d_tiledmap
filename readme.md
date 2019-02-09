# LibGdx/Ktx Example Using Box2d and Tile Map Polyline Collision

This example loads a tiled map which contains some object layers (floors, walls, shapes) that are made of polygon and 
polylines objects. The objects are converted into box2d shapes.

Box2d can easily render the shapes in the world by calling debugRender, and the box2d map can be rendered just by calling 
render(). These end up sharing the same coordinate system because they share the same camera.

The 'player' is just a dynamic body in the world whose linearVelocity is bound to key input. On mobile, the left and right 
sides of the screen act as a controller (this is taken from the superkoalio example).

My thoughts on using Box2d as a physics engine for your game is that if you build your game around box2d, you may end up 
regretting it - you simply can't emulate the 'ardcade physics' that's makes platforming work well, and with box2d it's pretty 
easy to get the world into 'freak out mode' if you start trying to do a lot with it.

Still, it's perfect for physics experiments, and with tiled maps integration you'll be able to set up a very complex 
environment. 
