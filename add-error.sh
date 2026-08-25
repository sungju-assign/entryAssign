#!/bin/bash

export LANG=C.UTF-8
export LC_ALL=C.UTF-8
export PYTHONIOENCODING=utf-8

BASE_DIR="src/main/java/com/sungjujjang/entryAssgin/global/exception"
ERROR_CODE_FILE="$BASE_DIR/ErrorCode.java"
EXCEPTIONS_DIR="$BASE_DIR/exceptions"

echo "=== ErrorCode & Exception 추가 도구 ==="
echo ""

read -p "에러코드 (예: NOT_FOUND): " ERROR_CODE
read -p "에러메시지 (예: 리소스가 없습니다): " ERROR_MSG
read -p "HTTP 상태코드 (예: 404): " STATUS_CODE
read -p "Exception 클래스 파일 이름 (예: NotFoundException): " CLASS_NAME

echo ""
echo "입력하신 정보:"
echo "  에러코드: $ERROR_CODE"
echo "  에러메시지: $ERROR_MSG"
echo "  상태코드: $STATUS_CODE"
echo "  클래스명: ${CLASS_NAME}.java"
echo ""

read -p "이대로 추가하시겠습니까? (y/n): " CONFIRM

if [ "$CONFIRM" != "y" ]; then
    echo "취소되었습니다."
    exit 1
fi

echo ""
echo "추가 중..."

ERROR_CODE="$ERROR_CODE" ERROR_MSG="$ERROR_MSG" STATUS_CODE="$STATUS_CODE" ERROR_CODE_FILE="$ERROR_CODE_FILE" python3 << 'PYEOF'
# -*- coding: utf-8 -*-
import re, sys, os
sys.stdout.reconfigure(encoding='utf-8')

error_code_file = os.environ["ERROR_CODE_FILE"]
status_code = os.environ["STATUS_CODE"]
error_code = os.environ["ERROR_CODE"]
error_msg = os.environ["ERROR_MSG"]

with open(error_code_file, 'r', encoding='utf-8') as f:
    lines = f.readlines()

entries = []
for line in lines:
    m = re.match(r'\s+([A-Z_]+)\((\d+),\s*"([^"]+)",\s*"([^"]+)"\)', line)
    if m:
        code, sc, ec, em = m.groups()
        entries.append({'code': code, 'status': int(sc), 'error_code': ec, 'error_msg': em})

entries.append({'code': error_code, 'status': int(status_code), 'error_code': error_code, 'error_msg': error_msg})

groups = {}
for e in entries:
    groups.setdefault(e['status'], []).append(e)

sorted_statuses = sorted(groups.keys())
for s in sorted_statuses:
    groups[s].sort(key=lambda x: x['code'])

output = []
output.append("package com.sungjujjang.entryAssgin.global.exception;")
output.append("")
output.append("import lombok.AllArgsConstructor;")
output.append("import lombok.Getter;")
output.append("")
output.append("@Getter")
output.append("@AllArgsConstructor")
output.append("public enum ErrorCode {")

total_groups = len(sorted_statuses)
for gi, status in enumerate(sorted_statuses):
    group_entries = groups[status]
    if gi > 0:
        output.append("")
    for ei, e in enumerate(group_entries):
        is_last = (gi == total_groups - 1) and (ei == len(group_entries) - 1)
        suffix = ";" if is_last else ","
        output.append('    {0}({1}, "{2}", "{3}"){4}'.format(e["code"], e["status"], e["error_code"], e["error_msg"], suffix))

output.append("")
output.append("    private Integer statusCode;")
output.append("    private String errorCode;")
output.append("    private String errorMessage;")
output.append("}")

with open(error_code_file, 'w', encoding='utf-8') as f:
    f.write("\n".join(output) + "\n")

print("ErrorCode.java 수정 완료")
PYEOF

EXCEPTION_FILE="$EXCEPTIONS_DIR/${CLASS_NAME}.java"

cat > "$EXCEPTION_FILE" << EOF
package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class ${CLASS_NAME} extends BusinessException {
    public static final BusinessException EXCEPTION = new ${CLASS_NAME}();
    public ${CLASS_NAME}() {
        super(ErrorCode.${ERROR_CODE});
    }
}
EOF

echo ""
echo "=== 완료! ==="
echo ""
echo "수정된 파일: $ERROR_CODE_FILE"
echo "생성된 파일: $EXCEPTION_FILE"
echo ""
echo "ErrorCode에 추가된 항목:"
grep "${ERROR_CODE}" "$ERROR_CODE_FILE"
