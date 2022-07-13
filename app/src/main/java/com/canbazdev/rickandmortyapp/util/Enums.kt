package com.canbazdev.rickandmortyapp.util

/*
*   Created by hamzacanbaz on 2.07.2022
*/
enum class LayoutManagers {
    LINEAR_LAYOUT_MANAGER, GRID_LAYOUT_MANAGER, EPISODE_LINEAR_LAYOUT_MANAGER
}

enum class Status {
    ALIVE, DEAD, UNKNOWN
}

enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN
}

enum class NestedCharacters(val isOpen:Int){
    OPEN(0), CLOSE(1)
}