# README #

Nombre:
=======
La idea principal es orientar la aplicación hacia todo público, no obstante, orientamos el público por preferéncia seran universitarios que viven solos
o comparten piso con otros, y aquellas personas que no saben cocinar pero se estan inciando en el arte de la cocina tradicional.
Los posibles nombre para la aplicación pueden ser:
1). Las recetas de la abuela.
2). Qué comemos hoy?
3). Cooking today.

Clases:
=======
- Receta
- Cjto de Recetas
- Ingredientes

SQLite: 
=======
Es imprescindible el uso de esta herramienta para poder realizar las consultas pertinentes de las posibles recetas, que como minimo seran 5.


Historias de usuaio:
====================
1). Como usuario quiero poder dar de alta una nueva receta para poder probar el sistema (desglosar).
	- Cuando se dé de alta una receta, el usuario escribira el nombre de la receta y escogera los ingredientes de una lista de ingredientes, ademas añadira la
	  preparación de la receta y una foto. Si no se añade un foto, se pondrá un por defecto. 
	- Cada receta tendra un identificador unico, el inconveniente de éste aspecto es que pueden haber dos recetas con el mismo nombre y pero diferente numero 
	  indentificativo.
	- (*) Para mejorar la aplicación, sera inprescindible añadir la opcion de poder clasificar la receta, es decir, si es de dieta mediterranea, indu, etc. Por
	  defecto se pondra en otros.

2). Como usuario quiero poder consultar una receta ya creada/existentes en el sistema para poder ver sus ingredientes (desglosar).
	- De cada receta se podra consultar todos sus atributos, al igual que se podran modificar.

3). Como usuario quiero poder modificar una receta ya creada/existente en el sistema para poder corregir algunos aspectos (desglosar).
	- De las recetas consultadas se podra modificar todos los campos, incluidos el nombre.
	- Si se modifica un ingrendiente, éste se puede: eliminar y sustituir por otro. En éste último caso, su sustituirá un ingrediente por una lista completa de ingredientes.

4). Como usuario quiero poder eliminar una receta ya creada/existente en el sistema para poder actualizarlo (desglosar).
	- Se pretende poder eliminar de manera permanente una receta del sistema.
	
5). Como usuario quiero poder listar una o varias recetas ya creadas/existentes en el sistema para poder ver su informacion (desglosar).
	- Se podran listar recetas por nombre, ingrediente(*) y por tipo de comida(*). En el listado obtenido apareceran las recetas con el nombre y la imagen que 
	  las representa.
	- (*) Se listaran las recetas con que no contengan dicho ingrediente.