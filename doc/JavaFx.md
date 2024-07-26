# Elements JavaFx

## Containers

### FlowPane

FlowPane est un conteneur (Container), il peut contenir des Control ou des autres conteneurs. Il organise les sous-composants consécutifs sur une ligne et appuie automatiquement les sous-composants jusqu'à la ligne suivante si la ligne actuelle est remplie.

* Organisation des éléments sur différentes lignes.
* La taille de chaque ligne est fonction de ce qu'elle contient
* Wrap au redimentionnement => **responsive**.
* Propriétés notables : Hgap Vgap 


### TilePane

TilePane est un conteneur (Container), TilePane est similaire à FlowPane. Il arrange les sous-composants consécutifs sur une ligne, et pousse automatiquement les sous-composants vers la ligne suivante si la ligne actuelle est remplie. Cependant, cela diffère de FlowPane parce que les sous-composants se trouvent sur la même taille de cellule.

* Organisation des éléments sur différentes lignes.
* La taille de chaque ligne est la même pour toutes
* Wrap au redimentionnement => **responsive**.
* Propriétés notables : Hgap Vgap


### StackPane

Pour superposer des composants (label / textField ?)
> Fusion des details/edit

### TitlePane

Element d'accordéon. Le contenu est un Pane

### ScrollPane

Pane avec ScrollBar

* Propriétés notables :
    * setHbarPolicy(ScrollBarPolicy.ALWAYS / NEVER / AS_NEEDED) 
    * setVbarPolicy(ScrollBarPolicy.ALWAYS / NEVER / AS_NEEDED)

### Accordeon

Conteneur de TilePanes

### ContextMenu

Menu qui s'ouvre au clique droit sur la zone

## Effects

* Blend
* Bloom
* BoxBlur
  * MotionBlur
  * GaussianBlur
* ColorAdjust
* ColorInput
* Glow
* ImageInput
* Lighting
* PerspectiveTransform
* Reflection
* SepiaTone
* Shadow
  * InnerShadow
  * DropShadow  