package com.simba.simbamedia.view.musicbanner.holder;

public interface MusicHolderCreator<VH extends MusicViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
