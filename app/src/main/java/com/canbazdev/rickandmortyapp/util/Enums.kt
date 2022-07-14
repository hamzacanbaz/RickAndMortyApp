package com.canbazdev.rickandmortyapp.util

/*
*   Created by hamzacanbaz on 2.07.2022
*/
enum class LayoutManagers(val text: String) {
    LINEAR_LAYOUT_MANAGER("Linear"), GRID_LAYOUT_MANAGER("Grid"), EPISODE_LINEAR_LAYOUT_MANAGER("Filter")
}

enum class Status {
    ALIVE, DEAD, UNKNOWN
}

enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN
}

enum class NestedCharacters(val isOpen: Int) {
    OPEN(0), CLOSE(1)
}

enum class States(val state: Int) {
    Success(1), Loading(0), Error(-1)
}