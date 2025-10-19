// 이 스크립트가 포함된 HTML의 DOM의 로딩이 완료되었으면 실행되는 함수
document.addEventListener("DOMContentLoaded", ()=> {
    document.querySelectorAll(".btn-edit").forEach( btn => {
        btn.addEventListener("click", (e)=>{
            e.preventDefault(); // 새로 고침 방지

            const row = btn.closest("tr"); // 현재 클릭된 수정버튼의 tr 엘리먼트를 찾음
            const id = row.getAttribute("data-id"); // 해당 행(row)로부터 id값을 설정함

            const nameInput = document.getElementById(`name-${id}`);    // <-- name-1  name-2 ...
            const memoInput = document.getElementById(`memo-${id}`);    // <-- memo-x
            if ( !nameInput || !memoInput) {
                console.error("입력 박스를 찾을 수 없습니다. : ", id);
                return;
            }

            const originName = nameInput.value;
            const originMemo = memoInput.value;

            // 기존에 span의 내용을 감추기
            row.querySelectorAll("span").forEach(elem => elem.classList.add("d-none"));

            // nameInput과 memoInput을 보이게 하기
            nameInput.classList.remove("d-none");
            memoInput.classList.remove("d-none");

            // nameInput에 포커싱하기
            nameInput.focus();
            nameInput.select();

            // 편집 버튼을 완료 버튼으로 변환 : 편집 버튼을 감추고 완료버튼을 보이게 하기
            btn.classList.add("d-none");

            // update form을 보이게 하기
            const updateForm = row.querySelector(".update-form");
            updateForm.classList.remove("d-none");

            // 수정 완료 버튼 클릭 이벤트 핸들러
            updateForm.addEventListener("submit", (evt)=>{
                const newName = nameInput.value;
                const newMemo = memoInput.value;

                if ( newName === originName && newMemo === originMemo ) {
                    // 값이 변경되지 않았따면 모든 엘리먼트(컨트롤, 컴포넌트)들을 원래대로 복귀시켜놓고 처리를 하지 않음
                    evt.preventDefault();   // 새로고침 방지
                    // ui 복구
                    nameInput.classList.add("d-none");  // nameInput 박스 숨기기
                    memoInput.classList.add("d-none");  // memoInput 박스 숨기기
                    //span 엘리먼트를 보이게하기
                    row.querySelectorAll("span").forEach(elem => elem.classList.remove("d-none"));
                    // form 감추기
                    updateForm.classList.add("d-none");
                    btn.classList.remove("d-none"); // edit 버튼을 나타내기
                    return;
                }

                // 변경된 값을 hidden input에 반영시킴
                updateForm.querySelector("input[name='name']").value = newName;
                updateForm.querySelector("input[name='memo']").value = newName;
            }, { onece: true });    // submit 핸들러 중복 방지

        });
    });
});