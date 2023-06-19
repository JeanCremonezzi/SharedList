package br.edu.ifsp.scl.sharedlist.adapter

interface OnTaskClickListenner {
    fun onEditMenuItemClick(position: Int)

    fun onRemoveMenuItemClick(position: Int)

    fun onCompleteMenuItemClick(position: Int)
}