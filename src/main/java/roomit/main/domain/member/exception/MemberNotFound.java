package roomit.main.domain.member.exception;

import roomit.main.global.error.ErrorCode;
import roomit.main.global.exception.CommonException;

public class MemberNotFound extends CommonException {

    public MemberNotFound() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
