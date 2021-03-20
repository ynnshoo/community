package com.ynns.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;
    public void setPagination(Integer totalPage, Integer currentPage) {
        this.totalPage=totalPage;
        this.currentPage=currentPage;

        pages.add(currentPage);
        for (int i=1;i<=3;i++){
            if(currentPage-i>0){
                pages.add(0,currentPage-i);
            }
            if (currentPage+i<=totalPage){
                pages.add(currentPage+i);
            }
        }

        //是否展示上一页
        if (currentPage==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页
        if (currentPage==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }
    }
}
