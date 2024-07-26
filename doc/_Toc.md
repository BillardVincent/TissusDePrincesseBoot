# Les tissus de Princesse

## Front
Liens entre les écrans

````mermaid
flowchart LR
subgraph "Menu principal"
    Ro[root] -->T(tissu) & F(fourniture) & P(projet) & Pa(Patron) & R(Rangement) & A(aide) & Pre(Préférences)
end

subgraph "Common"
    CM([commonAddMatiere])
    TiM([commonAddTissage])
    CB([ChoiceBox])
    CC([CouleurComp])
    CT([commonAddType])
end
    subgraph rangement
        R --> RL(list)
    end
subgraph tissu
T--> TL(list) --> TC(consult)
TS --filtre--> TL(list)
TL(list) --addNew-->  TE(edit)
TC --> TE 
TL-->TS(search)
end
TS -.type.- CB
TS -.tissage.- CB
TS -.matiere.- CB
TE <--> CM
TE <--> TiM
TE <--> CC

subgraph fourniture
F--> FL(list) --> FC(consult)
FS --filtre--> FL(list)
FL(list) --addNew-->  FE(edit)
FC --> FE
FL-->FS(search)
end
FE <-->CT
FS -.type.- CB

subgraph projet
P--> PL(list) --> PS(search) 
PL --> PC(consult) 
PS --filtre-->PL
end

subgraph patron
Pa--> PaL(list) --> PaC(consult)
PaS --filtre--> PaL(list)
PaL(list) --addNew-->  PaE(edit)
PaC --> PaE
PaL-->PaS(search)
PaE --> V(Version) --> TR(TissuRequis) & FR(FournitureRequise)
end

TR <--> CM
TR <--> TiM

PaC --nouveau projet--> PC
PaE --> RL

subgraph I [image]
IW(web)
IL(local)
IP(pressePapier)

end


TE --> RL
FE --> RL
RL --> TL & FL & PL & PaL
TE --> I
FE --> I
PaE --> I
 ````