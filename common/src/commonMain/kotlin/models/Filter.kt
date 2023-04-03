package models

data class Filter(
    var searchString: String = "",
    var dealSide: DealSide = DealSide.NONE
)