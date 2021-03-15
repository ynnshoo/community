package com.ynns.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer currentPage, Integer size) {
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>totalPage){
            currentPage=totalPage;
        }
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
