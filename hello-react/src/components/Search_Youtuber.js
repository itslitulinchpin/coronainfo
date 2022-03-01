import React, {Component} from'react';
import axios from 'axios'; // 액시오스
import Leftbottom from './minicomponents/Leftbottom';
import Lefttop from './minicomponents/Lefttop';

class Search_Youtuber extends Component{
    constructor(props){
        super(props);
        this.state={
            lists: [],
            addedlist:[]
        }
        this.ChangeMethod=this.ChangeMethod.bind(this);
       
    }

    ChangeMethod(name){
        this.state.addedlist=this.state.lists.concat(name+ " ");
        this.setState({
            lists:this.state.addedlist
         })
    }
    
    render(){

      


        const leftSidebar= {
            width: '525px',   /* 사이드바의 너비 */
            height:'900px',  /* 사이드바의 높이 */
            backgroundColor:'#e9e9e9',
            float: 'left', /* 왼쪽으로 플로팅 */
            marginLeft:'40px',
            
          }
          const leftContainer1 = {
            height:'100px',
            display: 'block',
            borderStyle: 'solid',
            borderWidth: 'thin',
          }
          const leftContainer2 = {
            height:'400px',
            display: 'block',
            borderStyle: 'solid',
            borderWidth: 'thin',
            overflow:'auto',
            
          }
         
          function transmit_youtuber_data(){
            var frm = new FormData(); 
            var searchingFile = document.getElementById("search_data").value; 
            frm.append("name", searchingFile);
            //테스트용 출력
            for (var pair of frm.entries()) {
                console.log(pair[0]+ ', ' + pair[1]);
              }
            axios(
                {
                  url: '/search_youtuber',
                  method: 'post',
                  data: {
                    frm
                  } , 
                  baseURL: 'http://localhost:8080'
                  //withCredentials: true,
                }
              ).then(function (response) {
                console.log(frm.get("name"))
              });
    
    
        }    
          
        return(
            <aside style = {leftSidebar}>
                <div style= {leftContainer1}>
                <h2>유투버를 검색하세요.</h2>		
                <form onSubmit = { function(e) {
                                e.preventDefault();
                                this.props.onSubmit(
                                    e.target.title.value
                                );
                                alert('Submit!');
                                transmit_youtuber_data();
                            }.bind(this)} >
                        <p><input type ="text" 
                            name="title"
                            size="35" 
                            placeholder="크리에이터 이름을 검색하세요"
                            id="search_data">
                            </input></p>
                        <p> <input type = "submit" value = "검색"></input></p>
                    </form>
                </div>

                <div style = {leftContainer2}>
                    <h2>유투버 검색결과</h2>

                    <Lefttop 
                        title= "감스트" 
                        tag = "피파, 롤, 개그" 
                        onChangePage = {this.ChangeMethod}>    
                    </Lefttop>
                    
                    <Lefttop 
                        title= "정형구tv" 
                        tag = "이산수학, 프로그래밍언어론" 
                        onChangePage = {this.ChangeMethod}> 
                    </Lefttop>

                    <Lefttop 
                        title= "김진석tv" 
                        tag = "컴퓨터알고리즘, 인터넷프로그래밍" 
                        onChangePage = {this.ChangeMethod}> 
                    </Lefttop>

                    <Lefttop 
                        title= "이병정tv" 
                        tag = "소프트웨어공학, 객체지향프로그래밍" 
                        onChangePage = {this.ChangeMethod}> 
                    </Lefttop>
                    
                    
                    
                    
                </div>
				<div style = {leftContainer2}>
                    <h2>내가 선택한 유투버 목록</h2>
                    {this.state.lists}
                </div>	
            
            </aside>
            
        );
    }
}

export default Search_Youtuber