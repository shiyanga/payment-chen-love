package bnb.comment.job.dao.groupwormhotel.mapper;

import bnb.comment.job.dto.wormhoteldb.BbbCommentTagsCommentAppendDto;

public interface BbbCommentTagsCommentAppendDtoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbbCommentTagsCommentAppendDto record);

    int insertSelective(BbbCommentTagsCommentAppendDto record);

    BbbCommentTagsCommentAppendDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbbCommentTagsCommentAppendDto record);

    int updateByPrimaryKey(BbbCommentTagsCommentAppendDto record);
}