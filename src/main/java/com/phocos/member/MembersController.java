package com.phocos.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class MembersController {

    @Autowired
    private MemberService memberService;
    
//    @GetMapping("/members")
//    public List<Member> getAllMembers() {
//        return memberService.findAll();
//    }
//
//    @GetMapping("/members/{memberID}")
//    public Member getMemberById(@PathVariable Integer memberID) {
//        return memberService.findById(memberID);
//    }
//
//    @PostMapping("/members/post")
//    public ResponseEntity<Member> createMember(@RequestBody Member member) {
//        memberService.insert(member);
//        return ResponseEntity.status(HttpStatus.CREATED).body(member);
//    }
//
//    @DeleteMapping("/members/delete/{memberID}")
//    public ResponseEntity<Void> deleteMember(@PathVariable Integer memberID) {
//        memberService.deleteById(memberID);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/members/edit/{memberID}")
//    public ResponseEntity<Member> updateMember(@PathVariable Integer memberID, @RequestBody Member updatedMember) {
//        Member member = memberService.findById(memberID);
//        if (member == null) {
//            return ResponseEntity.notFound().build();
//        }
//        member = memberService.updateMemberById(memberID, updatedMember);
//        return ResponseEntity.ok(member);
//    }
}