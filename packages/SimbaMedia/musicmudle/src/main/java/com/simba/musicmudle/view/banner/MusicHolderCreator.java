package com.simba.musicmudle.view.banner;

public interface MusicHolderCreator<VH extends MusicViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
