package bnb.comment.job.dao.groupwormhotel.mapper;

import bnb.comment.job.dto.wormhoteldb.BnbCommentTagsSpaceAppendDto;

public interface BnbCommentTagsSpaceAppendDtoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BnbCommentTagsSpaceAppendDto record);

    int insertSelective(BnbCommentTagsSpaceAppendDto record);

    BnbCommentTagsSpaceAppendDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BnbCommentTagsSpaceAppendDto record);

    int updateByPrimaryKey(BnbCommentTagsSpaceAppendDto record);
}