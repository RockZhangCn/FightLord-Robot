all: Main.class
	java Main
Main.class: Main.java Player.class Card.class
	javac Main.java
Player.class:Player.java Card.class CardModel.class
	javac Player.java 
Card.class: Card.java
	javac Card.java
CardModel.class: CardModel.java
	javac CardModel.java


clean: 
	rm *.class
