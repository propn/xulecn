﻿? / *     p A r r a y   * /  
  
 v a r   p A r r a y   =   C l a s s . c r e a t e ( ) ;  
 p A r r a y . p r o t o t y p e   =   {  
     i n i t i a l i z e   :   f u n c t i o n ( ) {  
 	 	 t h i s . f i r s t U n i t = n u l l ;  
 	 	 t h i s . l a s t U n i t = n u l l ;  
 	 	 t h i s . l e n g t h = 0 ;  
     } ,  
 	 g e t F i r s t U n i t   :   f u n c t i o n ( )   {   r e t u r n   t h i s . f i r s t U n i t ;   } ,  
 	 g e t L a s t U n i t   :   f u n c t i o n ( )   { 	 r e t u r n   t h i s . l a s t U n i t ;   } ,  
 	 g e t L e n g t h   :   f u n c t i o n ( )   { 	 r e t u r n   t h i s . l e n g t h ;   } ,  
 	 c l e a r A l l   :   f u n c t i o n ( ) {     p A r r a y . _ c l e a r A l l ( t h i s ) ;   } ,  
  
 	 / /W(旫坔~觛凬-c襋eN N*[鶎a  
 	 i n s e r t U n i t   :   f u n c t i o n ( m o d e ,   u n i t ,   n e w U n i t ) {  
 	 	 v a r   u 1 ,   u 2 ;  
 	 	 / /c襋eOMn  
 	 	 s w i t c h   ( m o d e ) {  
 	 	 	 c a s e   " b e g i n " : {  
 	 	 	 	 u 1 = n u l l ;  
 	 	 	 	 u 2 = t h i s . f i r s t U n i t ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 c a s e   " b e f o r e " : {  
 	 	 	 	 u 1 = ( u n i t ) ? u n i t . p r e v U n i t : n u l l ;  
 	 	 	 	 u 2 = u n i t ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 c a s e   " a f t e r " : {  
 	 	 	 	 u 1 = u n i t ;  
 	 	 	 	 u 2 = ( u n i t ) ? u n i t . n e x t U n i t : n u l l ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 d e f a u l t : {  
 	 	 	 	 u 1 = t h i s . l a s t U n i t ;  
 	 	 	 	 u 2 = n u l l ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
  
 	 	 n e w U n i t . p r e v U n i t = u 1 ;  
 	 	 n e w U n i t . n e x t U n i t = u 2 ;  
 	 	 i f   ( u 1 )  
 	 	 	 u 1 . n e x t U n i t = n e w U n i t ;  
 	 	 e l s e  
 	 	 	 t h i s . f i r s t U n i t = n e w U n i t ;  
 	 	 i f   ( u 2 )  
 	 	 	 u 2 . p r e v U n i t = n e w U n i t ;  
 	 	 e l s e  
 	 	 	 t h i s . l a s t U n i t = n e w U n i t ;  
 	 	 t h i s . l e n g t h + + ;  
 	 } ,  

 	 i n s e r t A r r a y   :   f u n c t i o n ( m o d e ,   u n i t ,   s u b A r r a y ) {  
 	 	 i f   ( ! s u b A r r a y   | |   ! s u b A r r a y . f i r s t U n i t )   r e t u r n ;  
  
 	 	 v a r   u 1 ,   u 2 ;  
 	 	 s w i t c h   ( m o d e ) {  
 	 	 	 c a s e   " b e g i n " : {  
 	 	 	 	 u 1 = n u l l ;  
 	 	 	 	 u 2 = t h i s . f i r s t U n i t ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 c a s e   " b e f o r e " : {  
 	 	 	 	 u 1 = ( u n i t ) ? u n i t . p r e v U n i t : n u l l ;  
 	 	 	 	 u 2 = u n i t ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 c a s e   " a f t e r " : {  
 	 	 	 	 u 1 = u n i t ;  
 	 	 	 	 u 2 = ( u n i t ) ? u n i t . n e x t U n i t : n u l l ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 d e f a u l t : {  
 	 	 	 	 u 1 = t h i s . l a s t U n i t ;  
 	 	 	 	 u 2 = n u l l ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
  
 	 	 s u b A r r a y . f i r s t U n i t . p r e v U n i t = u 1 ;  
 	 	 s u b A r r a y . l a s t U n i t . n e x t U n i t = u 2 ;  
 	 	 i f   ( u 1 )   u 1 . n e x t U n i t = s u b A r r a y . f i r s t U n i t ;   e l s e   t h i s . f i r s t U n i t = s u b A r r a y . f i r s t U n i t ;  
 	 	 i f   ( u 2 )   u 2 . p r e v U n i t = s u b A r r a y . l a s t U n i t ;   e l s e   t h i s . l a s t U n i t = s u b A r r a y . l a s t U n i t ;  
 	 	 t h i s . l e n g t h + = s u b A r r a y . l e n g t h ;  
 	 } ,  
 	 d e l e t e U n i t   :   f u n c t i o n ( u n i t ) {  
 	 	 v a r   u 1 ,   u 2 ;  
 	 	 u 1 = u n i t . p r e v U n i t ;  
 	 	 u 2 = u n i t . n e x t U n i t ;  
 	 	 i f   ( u 1 )   u 1 . n e x t U n i t = u 2 ;   e l s e   t h i s . f i r s t U n i t = u 2 ;  
 	 	 i f   ( u 2 )   u 2 . p r e v U n i t = u 1 ;   e l s e   t h i s . l a s t U n i t = u 1 ;  
 	 	 t h i s . l e n g t h - - ;  
 	 	 d e l e t e   u n i t ;  
 	 } ,  
 	 i n s e r t W i t h D a t a   :   f u n c t i o n ( d a t a ) {  
 	 	 v a r   f o u n d = f a l s e ;  
 	 	 v a r   _ u n i t = t h i s . f i r s t U n i t ;  
 	 	 w h i l e   ( _ u n i t ) {  
 	 	 	 i f   ( _ u n i t . d a t a = = d a t a ) {  
 	 	 	 	 f o u n d = t r u e ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 _ u n i t = _ u n i t . n e x t U n i t ;  
 	 	 }  
  
 	 	 v a r   n e w U n i t = { } ;  
 	 	 n e w U n i t . d a t a = d a t a ;  
 	 	 i f   ( ! f o u n d )   t h i s . i n s e r t U n i t ( " e n d " ,   n u l l ,   n e w U n i t ) ;  
 	 } ,  
 	 d e l e t e B y D a t a   :   f u n c t i o n ( d a t a ) {  
 	 	 v a r   _ u n i t = t h i s . f i r s t U n i t ;  
 	 	 w h i l e   ( _ u n i t ) {  
 	 	 	 i f   ( _ u n i t . d a t a = = d a t a ) {  
 	 	 	 	 t h i s . d e l e t e U n i t ( _ u n i t ) ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 _ u n i t = _ u n i t . n e x t U n i t ;  
 	 	 }  
 	 }  
 }  
  
 p A r r a y . _ c l e a r A l l   =   f u n c t i o n ( p A r r ) {  
 	 v a r   u n i t = p A r r . f i r s t U n i t ;  
 	 v a r   _ u n i t ;  
 	 w h i l e   ( u n i t ) {  
 	 	 _ u n i t = u n i t ;  
 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 i f   ( _ u n i t . d a t a )   d e l e t e   _ u n i t . d a t a ;  
 	 	 d e l e t e   _ u n i t ;  
 	 }  
 	 p A r r . f i r s t U n i t = n u l l ;  
 	 p A r r . l a s t U n i t = n u l l ;  
 	 p A r r . l e n g t h = 0 ;  
 }  
  
 / *     P a r a m e t e r S e t     * /  
  
 v a r   P a r a m e t e r S e t   =   C l a s s . c r e a t e ( ) ;  
  
 P a r a m e t e r S e t . p r o t o t y p e   =   {  
 	 i n i t i a l i z e   :   f u n c t i o n ( )   {  
 	 	 t h i s . _ p a r a m e t e r s   =   [ ] ;  
 	 } ,  
  
 	 / /X濺燦 N*S耬p[鶎a  
 	 _ a d d P a r a m e t e r   :   f u n c t i o n ( n a m e )   {  
 	 	 p a r a m e t e r   =   { } ;  
 	 	 p a r a m e t e r . d a t a T y p e   =   " s t r i n g " ;  
 	 	 p a r a m e t e r . n a m e   =   n a m e ;  
 	 	 v a r   p r o p e r t y   =   " _ _ "   +   n a m e ;  
 	 	 v a r   _ p a r a m e t e r s   =   t h i s . _ p a r a m e t e r s ;  
 	 	 _ p a r a m e t e r s [ p r o p e r t y ]   =   p a r a m e t e r ;  
 	 	 _ p a r a m e t e r s [ _ p a r a m e t e r s . l e n g t h ]   =   p a r a m e t e r ;  
 	 	 r e t u r n   p a r a m e t e r ;  
 	 } ,  
  
 	 / /R c塏 N*S耬p[鶎a  
 	 d e l P a r a m e t e r   :   f u n c t i o n ( n a m e )   {  
 	         v a r   s t a r t I n d e x   =   n u l l ;  
 	         v a r   o l d _ p a r a m e t e r s   =   t h i s . _ p a r a m e t e r s ;  
 	         t h i s . _ p a r a m e t e r s   =   [ ] ;  
 	         f o r ( v a r   i = 0 ;   i < o l d _ p a r a m e t e r s . l e n g t h ;   i + + ) {  
 	             i f ( o l d _ p a r a m e t e r s [ i ] . n a m e ! = n a m e ) {  
 	                 t h i s . s e t D a t a T y p e ( o l d _ p a r a m e t e r s [ i ] . n a m e ,   o l d _ p a r a m e t e r s [ i ] . d a t a T y p e ) ;  
 	                 t h i s . s e t V a l u e ( o l d _ p a r a m e t e r s [ i ] . n a m e ,   o l d _ p a r a m e t e r s [ i ] . v a l u e ) ;  
 	             }  
 	         }  
 	 } ,  
  
 	 / /兎S諲 N*S耬p[鶎a  
 	 _ g e t P a r a m e t e r   :   f u n c t i o n ( n a m e )   {  
 	 	 v a r   _ p a r a m e t e r s   =   t h i s . _ p a r a m e t e r s ;  
 	 	 i f   ( t y p e o f ( n a m e )   = =   " n u m b e r " ) {  
 	 	 	 v a r   i n d e x   =   S y s t e m . g e t I n t ( n a m e ) ;  
 	 	 	 v a r   p a r a m e t e r   =   _ p a r a m e t e r s [ i n d e x ] ;  
 	 	 	 r e t u r n   p a r a m e t e r ;  
 	 	 }  
 	 	 e l s e {  
 	 	 	 v a r   p r o p e r t y   =   " _ _ "   +   n a m e ;  
 	 	 	 v a r   p a r a m e t e r   =   _ p a r a m e t e r s [ p r o p e r t y ] ;  
 	 	 	 r e t u r n   p a r a m e t e r ;  
 	 	 }  
 	 } ,  
  
 	 / /徳V轘耬pv凬*ep  
 	 c o u n t   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . _ p a r a m e t e r s . l e n g t h ;  
 	 } ,  
  
 	 / /?徢S耬pv i n d e x兎S諷耬pv凾y  
 	 i n d e x T o N a m e   :   f u n c t i o n ( i n d e x )   {  
 	 	 v a r   p a r a m e t e r   =   t h i s .  . _ g e t P a r a m e t e r ( i n d e x ) ;  
 	 	 i f   ( p a r a m e t e r )   {  
 	 	 	 r e t u r n   p a r a m e t e r . n a m e ;  
 	 	 }  
 	 } ,  
  
 	 / /~賑[歋耬p嬀nS耬pP<  
 	 s e t V a l u e   :   f u n c t i o n ( n a m e ,   v a l u e )   {  
 	 	 v a r   p a r a m e t e r   =   t h i s . _ g e t P a r a m e t e r ( n a m e ) ;  
 	 	 i f   ( ! p a r a m e t e r   & &   t y p e o f ( n a m e )   ! =   " n u m b e r " )   {  
 	 	 	 p a r a m e t e r   =   t h i s . _ a d d P a r a m e t e r ( n a m e ) ;  
 	 	 }  
 	 	 i f   ( p a r a m e t e r ) {  
 	 	 	 p a r a m e t e r . v a l u e   =   v a l u e ;  
 	 	 }  
 	 } ,  
  
 	 / /兎S謈[歋耬pv凱<  
 	 g e t V a l u e   :   f u n c t i o n ( n a m e )   {  
 	 	 v a r   p a r a m e t e r   =   t h i s . _ g e t P a r a m e t e r ( n a m e ) ;  
 	 	 i f   ( p a r a m e t e r )   {  
 	 	 	 r e t u r n   p a r a m e t e r . v a l u e ;  
 	 	 }  
 	 } ,  
  
 	 / *  
 	 d a t e :  
 	 n u m b e r :  
 	 b o o l e a n :  
 	 s t r i n g :  
 	 * /  
 	 / /c[歋耬pTy餞孲耬pv別pcn|{W媂濺燦 N*S耬pX濺燬耬pN錞_艠{?徢 s e t V a l u e~賁耬p嬀nS耬pP<	  
 	 s e t D a t a T y p e   :   f u n c t i o n ( n a m e ,   d a t a T y p e )   {  
 	 	 v a r   p a r a m e t e r   =   t h i s . _ g e t P a r a m e t e r ( n a m e ) ;  
 	 	 i f   ( ! p a r a m e t e r   & &   t y p e o f ( n a m e )   ! =   " n u m b e r " )   {  
 	 	 	 p a r a m e t e r   =   t h i s . _ a d d P a r a m e t e r ( n a m e ) ;  
 	 	 }  
 	 	 i f   ( p a r a m e t e r ) {  
 	 	 	 p a r a m e t e r . d a t a T y p e   =   d a t a T y p e ;  
 	 	 }  
 	 } ,  
  
 	 / /兎S謈[歋耬pv別pcn|{W  
 	 g e t D a t a T y p e   :   f u n c t i o n ( n a m e )   {  
 	 	 v a r   p a r a m e t e r   =   t h i s . _ g e t P a r a m e t e r ( n a m e ) ;  
 	 	 i f   ( p a r a m e t e r )   {  
 	 	 	 r e t u r n   p a r a m e t e r . d a t a T y p e ;  
 	 	 }  
 	 }  
 }  
  
 / *   F i e l d   * /  
  
 v a r   F i e l d   =   C l a s s . c r e a t e ( ) ;  
 F i e e l d . p r o t o t y p e   =   {  
  
     i n i t i a l i z e   :   f u n c t i o n ( ) { } ,  
  
 	 g e t N a m e   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . n a m e ;  
 	 } ,  
  
 	 g e t L a b e l   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . l a b e l ;  
 	 } ,  
  
 	 g e t D a t a T y p e   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . d a t a T y p e ;  
 	 } ,  
  
 	 g e t E d i t o r T y p e   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . e d i t o r T y p e ;  
 	 } ,  
  
 	 i s R e a d O n l y   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . r e a d O n l y ;  
 	 } ,  
  
 	 s e t R e a d O n l y   :   f u n c t i o n ( r e a d O n l y )   {  
 	 	 v a r   d a t a s e t   =   t h i s . d a t a s e t ;  
 	 	 d a t a s e t . s e t F i e l d R e a d O n l y ( t h i s . n a m e ,   r e a d O n l y ) ;  
 	 } ,  
  
 	 g e t D e f a u l t V a l u e   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . d e f a u l e V a l u e ;  
 	 } ,  
  
 	 s e t D e f a u l t V a l u e   :   f u n c t i o n ( d e f a u l e V a l u e )   {  
 	 	  
 	 	 t h i s . d e f a u l e V a l u e = d e f a u l e V a l u e ;  
 	 } ,  
  
 	 i s R e q u i r e d   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . r e q u i r e d ;  
 	 } ,  
  
 	 s e t R e q u i r e d   :   f u n c t i o n ( r e q u i r e d )   {  
 	 	 t h i s . r e q u i r e d = r e q u i r e d ;  
 	 } ,  
  
  
 	 g e t F o r m a t   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . f o r m a t ;  
 	 } ,  
 	 s e t F o r m a t   :   f u n c t i o n ( f o r m a t )   {  
 	 	 t h i s . f o r m a t = f o r m a t ;  
 	 } ,  
  
 	 i s V a l u e P r o t e c t e d   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . v a l u e P r o t e c t e d ;  
 	 } ,  
  
 	 s e t V a l u e P r o t e c t e d   :   f u n c t i o n ( v a l u e P r o t e c t e d )   {  
 	 	 t h i s . v a l u e P r o t e c t e d = v a l u e P r o t e c t e d ;  
 	 } ,  
  
 	 i s V i s i b l e   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . v i s i b l e ;  
 	 } ,  
  
 	 g e t D r o p D o w n   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . d r o p D o w n ;  
 	 } ,  
  
 	 g e t T a g   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . t a g ;  
 	 } ,  
  
 	 s e t T a g   :   f u n c t i o n ( t a g )   {  
 	 	 t h i s . t a g   =   t a g ;  
 	 } ,  
  
 	 g e t T o o l T i p   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . t o o l T i p ;  
 	 } ,  
  
 	 s e t T o o l T i p   :   f u n c t i o n ( t o o l T i p )   {  
 	 	 t h i s . t o o l T i p   =   t o o l T i p ;  
 	 } ,  
  
 	 g e t A l i g n   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . a l i g n ;  
 	 } ,  
  
 	 s e t A l i g n   :   f u n c t i o n ( a l i g n )   {  
 	 	 t h i s . a l i g n   =   a l i g n ;  
 	 } ,  
  
 	 g e t V A l i g n   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . v a l i g n ;  
 	 } ,  
 	 s e t V A l i g n   :   f u n c t i o n ( v a l i g n )   {  
 	 	 t h i s . v a l i g n   =   v a l i g n ;  
 	 } , 	  
 	 g e t I s R e s u l t   :   f u n c t i o n ( )   {  
 	 	 r e t u r n   t h i s . i s R e s u l t ;  
 	 } ,  
 	 s e t R e s u l t   :   f u n c t i o n ( i s R e s u l t )   {  
 	 	 t h i s . i s R e s u l t   =   i s R e s u l t ;  
 	 } ,  
 	 g e t R e s u l t C o d e : f u n c t i o n ( ) {  
 	 	 r e t u r n   t h i s . r e s u l t C o d e ;  
 	 } ,  
 	 s e t R e s u l t C o d e   :   f u n c t i o n ( r e s u l t C o d e )   {  
 	 	 t h i s . r e s u l t C o d e   =   r e s u l t C o d e ;  
 	 }  
 	  
 	  
 	  
 }  
  
 / *   R e c o r d   * /  
  
  
 R e c o r d   =   {  
  
 	 g e t V a l u e   :   f u n c t i o n ( f i e l d N a m e ) {  
 	 	 v a r   r e c o r d   =   t h i s ;  
 	 	 v a r   d a t a s e t = r e c o r d . d a t a s e t ;  
 	 	 v a r   f i e l d s = r e c o r d . f i e l d s ;  
 	 	 v a r   f i e l d I n d e x = - 1 ;  
 	 	 v a r   v a l u e ;  
 	 	 i f   ( t y p e o f ( f i e l d N a m e ) = = " n u m b e r " ) {  
 	 	 	 f i e l d I n d e x = f i e l d N a m e ;  
 	 	 }  
 	 	 e l s e   i f   ( t y p e o f ( f i e l d N a m e ) = = " s t r i n g " ) {  
 	 	 	 f i e l d I n d e x = f i e l d s [ " _ i n d e x _ " + f i e l d N a m e ] ;  
 	 	 }  
 	 	 v a r   f i e l d = f i e l d s [ f i e l d I n d e x ] ;  
 	 	  
 	 	 i f   ( t y p e o f ( f i e l d ) = = " u n d e f i n e d " ) {  
 	 	 	 r e t u r n   " " ;  
 	 	 }  
 	 	 v a l u e = r e c o r d [ f i e l d I n d e x ] ;  
 	 	 i f   ( t y p e o f ( v a l u e ) = = " u n d e f i n e d "   | |   v a l u e = = n u l l   | |   ( t y p e o f ( v a l u e ) = = " n u m b e r "   & &   i s N a N ( v a l u e ) ) )   {  
 	 	 	 v a l u e = " " ;  
 	 	 }  
 	 	 r e t u r n   v a l u e ;  
 	 } ,  
  
 	 g e t S t r i n g   :   f u n c t i o n ( f i e l d N a m e ) {  
 	 	 v a r   r e c o r d = t h i s ,   f i e l d ,   v a l u e = " " ;  
 	 	 v a r   f i e l d = r e c o r d . d a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
  
 	 	 i f   ( f i e l d ) {  
 	 	 	 v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
  
 	 	 	 i f   ( v a l u e ! = " " )   {  
 	 	 	 	 s w i t c h   ( f i e l d . d a t a T y p e ) {  
 	 	 	 	 	 c a s e   " i n t " : {  
 	 	 	 	 	 	 i f   ( ! i s N a N ( v a l u e ) )   v a l u e = v a l u e + " " ;  
 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
 	 	 	 	 	 d e f a u l t : {  
 	 	 	 	 	 	 v a l u e = S y s t e m . g e t V a l i d S t r ( v a l u e ) ;  
 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 }  
 	 	 }  
 	 	 r e t u r n   v a l u e ;  
 	 } ,  
  
 	 _ s e t V a l u e   :   f u n c t i o n ( f i e l d N a m e ,   v a l u e ) {  
 	 	 v a r   r e c o r d   =   t h i s ;  
  
 	 	 v a r   d a t a s e t = r e c o r d . d a t a s e t ;  
 	 	 v a r   f i e l d s = r e c o r d . f i e l d s ;  
 	 	 v a r   f i e l d I n d e x = - 1 ;  
  
 	 	 i f   ( t y p e o f ( f i e l d N a m e ) = = " n u m b e r " ) {  
 	 	 	 f i e l d I n d e x = f i e l d N a m e ;  
 	 	 }  
 	 	 e l s e   i f   ( t y p e o f ( f i e l d N a m e ) = = " s t r i n g " ) {  
 	 	 	 f i e l d I n d e x = f i e l d s [ " _ i n d e x _ " + f i e l d N a m e ] ;  
 	 	 }  
  
 	 	 i f   ( t y p e o f ( f i e l d s [ f i e l d I n d e x ] ) = = " u n d e f i n e d " ) {  
 	 	 	 t h r o w   C o n s t . E r r C a n t F i n d F i e l d . r e p l a c e ( " % s " ,   r e c o r d . d a t a s e t . i d + " . " + f i e l d N a m e ) ;  
 	 	 }  
  
 	 	 v a r   f i e l d = f i e l d s [ f i e l d I n d e x ] ;  
 	 	 i f ( f i e l d . d a t a T y p e = = " i n t " )  
 	 	 {  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " n u m b e r " )   {  
 	 	 	 	 v a l u e   =   S y s t e m . g e t I n t ( v a l u e ) ;  
 	 	 	 }  
 	 	 }  
  
 	 	 r e c o r d [ f i e l d I n d e x ] = v a l u e ;  
 	 } ,  
  
 	 s e t V a l u e   :   f u n c t i o n ( f i e l d N a m e ,   v a l u e ) {  
 	 	 v a r   r e c o r d   =   t h i s ;  
  
 	 	 v a r   d a t a s e t = r e c o r d . d a t a s e t ;  
 	 	 v a r   f i e l d s = r e c o r d . f i e l d s ;  
 	 	 v a r   f i e l d I n d e x = - 1 ;  
  
 	 	 i f   ( t y p e o f ( f i e l d N a m e ) = = " n u m b e r " ) {  
 	 	 	 f i e l d I n d e x = f i e l d N a m e ;  
 	 	 }  
 	 	 e l s e   i f   ( t y p e o f ( f i e l d N a m e ) = = " s t r i n g " ) {  
 	 	 	 f i e l d I n d e x = f i e l d s [ " _ i n d e x _ " + f i e l d N a m e ] ;  
 	 	 }  
  
 	 	 i f   ( t y p e o f ( f i e l d s [ f i e l d I n d e x ] ) = = " u n d e f i n e d " ) {  
 	 	 	 t h r o w   C o n s t . E r r C a n t F i n d F i e l d . r e p l a c e ( " % s " ,   r e c o r d . d a t a s e t . i d + " . " + f i e l d N a m e ) ;  
 	 	 }  
  
 	 	 v a r   f i e l d = f i e l d s [ f i e l d I n d e x ] ;  
 	 	 i f ( f i e l d . d a t a T y p e = = " i n t " )  
 	 	 {  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " n u m b e r " )   {  
 	 	 	 	 v a l u e   =   S y s t e m . g e t I n t ( v a l u e ) ;  
 	 	 	 }  
 	 	 }  
  
  
 	 	 r e c o r d [ f i e l d I n d e x ] = v a l u e ;  
 	 	 d a t a s e t . m o d i f i e d = t r u e ;  
  
 	 	 i f   ( d a t a s e t . s t a t e = = " n o n e " ) {  
 	 	 	 d a t a s e t . s t a t e = " m o d i f y " ;  
 	 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 	 }  
 	 	 D a t a s e t . b r o a d c a s t F i e l d M s g ( D a t a s e t . n o t i f y F i e l d D a t a C h a n g e d ,   d a t a s e t ,   r e c o r d ,   f i e l d ) ;  
 	 } ,  
  
 	 g e t P r e v R e c o r d   :   f u n c t i o n ( ) {  
 	 	 v a r   r e c o r d = t h i s ;  
 	 	 w h i l e   ( r e c o r d ) {  
 	 	 	 r e c o r d = r e c o r d . p r e v U n i t ;  
 	 	 	 i f   ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) )   r e t u r n   r e c o r d ;  
 	 	 }  
 	 } ,  
  
 	 g e t N e x t R e c o r d   :   f u n c t i o n ( ) {  
 	 	 v a r   r e c o r d = t h i s ;  
 	 	 w h i l e   ( r e c o r d ) {  
 	 	 	 r e c o r d = r e c o r d . n e x t U n i t ;  
 	 	 	 i f   ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) )   r e t u r n   r e c o r d ;  
 	 	 }  
 	 } ,  
  
 	 g e t D a t a s e t   :   f u n c t i o n ( ) {  
 	 	 r e t u r n   t h i s . d a t a s e t ;  
 	 } ,  
  
 	 g e t P a g e I n d e x   :   f u n c t i o n ( ) {  
 	 	 r e t u r n   t h i s . p a g e I n d e x ;  
 	 } ,  
  
 	 g e t S t a t e   :   f u n c t i o n ( ) {  
 	 	 r e t u r n   t h i s . r e c o r d S t a t e ;  
 	 } ,  
  
 	 s e t S t a t e   :   f u n c t i o n ( s t a t e )   {  
 	 	 t h i s . r e c o r d S t a t e   =   s t a t e ;  
 	 } ,  
  
         r e s e t S t a t e   :   f u n c t i o n ( r e c o r d ) {  
 	 	 i f   ( r e c o r d . r e c o r d S t a t e = = " d e l e t e " )   {  
 	 	 	 r e c o r d . r e c o r d S t a t e = " d i s c a r d " ;  
 	 	 }  
 	 	 e l s e   i f   ( r e c o r d . r e c o r d S t a t e ! = " d i s c a r d " )   {  
 	 	 	 r e c o r d . r e c o r d S t a t e = " n o n e " ;  
 	 	 }  
 	 } ,  
      
     / /嬀n坔h<圠嫲_U[Wk祐凷陭鸤^`'  
     s e t F i e l d R e a d O n l y :   f u n c t i o n ( t a b l e , c e l l N a m e , r e a d O n l y ) {  
    
         v a r   c e l l   =   t h i s . g e t C e l l ( t a b l e , c e l l N a m e ) ;  
         i f ( c e l l ) {  
             c e l l . d i s a b l e d = r e a d O n l y ;  
               	  
            
         }  
     } ,  
      
     / /兎S謭hh<圠嫲_U[Wk祐剗"_  
     g e t C e l l I n d e x :   f u n c t i o n ( t a b l e , c e l l N a m e ) {  
         v a r   c e l l I n d e x = n u l l ;  
         v a r   r e c o r d = t h i s ;  
          
         v a r   r o w = D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( t a b l e , r e c o r d ) ;  
         i f   ( t y p e o f ( c e l l N a m e ) = = " n u m b e r " ) {  
             c e l l I n d e x = c e l l N a m e ;  
         }  
         e l s e   i f   ( t y p e o f ( c e l l N a m e ) = = " s t r i n g " ) {  
             f o r ( v a r   i = 0 ;   ( r o w )   & &   ( r o w . c e l l s )   & &   i < r o w . c e l l s . l e n g t h ;   i + + ) {  
                 i f ( r o w . c e l l s [ i ] . n a m e = = c e l l N a m e ) {  
                     c e l l I n d e x = i ;  
                     b r e a k ;  
                 }  
             }  
         } e l s e {  
             a l e r t ( "S耬p [ c e l l N a m e ]|{W嫊嬶 " ) ;  
         }  
         r e t u r n   c e l l I n d e x ;  
     } ,  
      
     / /兎S謭hh<圠SUQCh<  
     g e t C e l l :   f u n c t i o n ( t a b l e , c e l l N a m e ) {  
         v a r   c e l l = n u l l ;  
         v a r   r e c o r d = t h i s ;  
          
         v a r   r o w = D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( t a b l e , r e c o r d ) ;  
         i f   ( t y p e o f ( c e l l N a m e ) = = " n u m b e r " ) {  
             c e l l = r o w . c e l l [ c e l l N a m e ] ;  
         }  
         e l s e   i f   ( t y p e o f ( c e l l N a m e ) = = " s t r i n g " ) {  
             f o r ( v a r   i = 0 ;   ( r o w )   & &   ( r o w . c e l l s )   & &   i < r o w . c e l l s . l e n g t h ;   i + + ) {  
                 i f ( r o w . c e l l s [ i ] . n a m e = = c e l l N a m e ) {  
                     c e l l = r o w . c e l l s [ i ] ;  
                     b r e a k ;  
                 }  
             }  
         } e l s e {  
             a l e r t ( "S耬p [ c e l l N a m e ]|{W嫊嬶 " ) ;  
         }  
         r e t u r n   c e l l ;  
     }  
      
 }  
  
 f u n c t i o n   i n i t R e c o r d ( r e c o r d ,   d a t a s e t ) {  
 	 r e c o r d . d a t a s e t = d a t a s e t ;  
 	 r e c o r d . f i e l d s = d a t a s e t . f i e l d s ;  
 	 r e c o r d . p a g e I n d e x = d a t a s e t . p a g e I n d e x ;  
 	 r e c o r d . v i s i b l e = t r u e ;  
  
 	 r e c o r d . g e t V a l u e = R e c o r d . g e t V a l u e ;  
 	 r e c o r d . g e t S t r i n g = R e c o r d . g e t S t r i n g ;  
 	 r e c o r d . s e t V a l u e = R e c o r d . s e t V a l u e ;  
  
 	 r e c o r d . g e t P r e v R e c o r d = R e c o r d . g e t P r e v R e c o r d ;  
 	 r e c o r d . g e t N e x t R e c o r d = R e c o r d . g e t N e x t R e c o r d ;  
  
 	 r e c o r d . g e t D a t a s e t = R e c o r d . g e t D a t a s e t ;  
 	 r e c o r d . g e t P a g e I n d e x = R e c o r d . g e t P a g e I n d e x ;  
 	 r e c o r d . g e t S t a t e = R e c o r d . g e t S t a t e ;  
 	 r e c o r d . s e t S t a t e = R e c o r d . s e t S t a t e ;  
  
 	 r e c o r d . _ s e t V a l u e = R e c o r d . _ s e t V a l u e ;  
      
     r e c o r d . g e t C e l l = R e c o r d . g e t C e l l ;  
     r e c o r d . g e t C e l l I n d e x = R e c o r d . g e t C e l l I n d e x ;  
     r e c o r d . s e t F i e l d R e a d O n l y = R e c o r d . s e t F i e l d R e a d O n l y ;  
      
 	 f o r ( v a r   j = 0 ;   j < r e c o r d . l e n g t h - 1 ;   j + + ) {  
 	 	 i f   ( r e c o r d [ j ]   & &   r e c o r d [ j ] ! = " " )   {  
 	 	 	 s w i t c h   ( d a t a s e t . g e t F i e l d ( j ) . d a t a T y p e ) {  
 	 	 	 	 c a s e   " i n t " : {  
 	 	 	 	 	 r e c o r d [ j ] = S y s t e m . g e t I n t ( r e c o r d [ j ] ) ;  
 	 	 	 	 	 b r e a k ;  
 	 	 	 	 }  
 	 	 	 	 d e f a u l t : {  
 	 	 	 	 	 r e c o r d [ j ] = S y s t e m . g e t D e c o d e S t r ( r e c o r d [ j ] ) ;  
 	 	 	 	 	 b r e a k ;  
 	 	 	 	 }  
 	 	 	 }  
 	 	 }  
 	 }  
 }  
  
 f u n c t i o n   c r e a t e D a t a s e t ( o p t )   {  
 	  
         v a r   d a t a s e t   =   n e w   p A r r a y ( ) ;  
  
 	 	 d a t a s e t . t y p e   =   o p t . t   | |   ' r e f e r e n c e '   ;  
 	 	 d a t a s e t . r e a d O n l y   =   o p t . r   | |   f a l s e   ;  
 	 	 d a t a s e t . e d i t a b l e   =   o p t . e   | |   f a l s e   ;  
 	 	  
 	 	 i f ( t y p e o f ( o p t . a s y n c )   = =   ' u n d e f i n e d '   )  
         	 	 d a t a s e t . a s y n c =     t r u e ;  
         	 e l s e  
                 	 d a t a s e t . a s y n c =     o p t . a s y n c ; 	  
          
 	 	 d a t a s e t . l o a d D a t a A c t i o n   =   o p t . l a   | |   ' '   ;  
 	 	 d a t a s e t . _ q u e r y T y p e   =   o p t . q t   | |   ' d a t a s e t '   ;  
 	 	 d a t a s e t . p a g i n a t e   =   o p t . p   | |   f a l s e   ;  
 	 	 i f ( t y p e o f ( o p t . s d )   = =   ' u n d e f i n e d '   )  
         	 	 d a t a s e t . s t a t i c D a t a S o u r c e =     t r u e ;  
         	 e l s e  
                 	 d a t a s e t . s t a t i c D a t a S o u r c e =     o p t . s d ; 	  
                 	  
 	 	 d a t a s e t . x m l F o r m a t   =   o p t . x   | |   ' r e c o r d s '   ;  
 	 	 d a t a s e t . l o a d A s N e e d e d   =   o p t . l n   | |   f a l s e   ;  
 	 	 d a t a s e t . m a s t e r D a t a s e t   =   o p t . m d   | |   ' '   ;  
 	 	 d a t a s e t . m a s t e r K e y F i e l d s   =   o p t . m f   | |   ' '   ;  
 	 	 d a t a s e t . d e t a i l K e y F i e l d s   =   o p t . d f   | |   ' '   ;  
 	 	  
 	 	 d a t a s e t . l o a d D a t a A c t i o n M e t h o d   =   o p t . l m   | |   ' '   ;  
 	 	 	 	  
         d a t a s e t . f i e l d s   =   [ ] ;  
         d a t a s e t . f i e l d s . c o u n t   =   D a t a s e t . f i e l d _ c o u n t ;  
         d a t a s e t . _ p a r a m e t e r s   =   n e w   P a r a m e t e r S e t ( ) ;  
  
         d a t a s e t . _ q u e r y F i e l d s   =   [ ] ; / /u(N嶵gRhz颫 ? 墎兎S講別pcn[Wk  
  
         d a t a s e t . u p d a t e I t e m s   =   [ ] ;  
         d a t a s e t . f i e l d s . f i e l d C o u n t   =   0 ;  
         d a t a s e t . p a g e S i z e   =   o p t . p s   | |   9 9 9 9 ;  
         d a t a s e t . p a g e C o u n t   =   o p t . p c   | |   1 ;  
         d a t a s e t . p a g e I n d e x   =   o p t . p i   | |   1 ;  
         d a t a s e t . r e c o r d C o u n t   =   o p t . r c   | |   0   ;  
         d a t a s e t . a u t o L o a d P a g e   =   o p t . a l   | |   f a l s e ;  
         d a t a s e t . d i s a b l e C o n t r o l C o u n t   =   0 ;  
         d a t a s e t . d i s a b l e E v e n t C o u n t   =   0 ;  
  
         d a t a s e t . m a s k C o n t r o l   =   o p t . m c   | |   f a l s e ;  
  
 	 d a t a s e t . a d d F i e l d = D a t a s e t . p r o t o t y p e . a d d F i e l d ;  
  
 	 d a t a s e t . g e t P r e v R e c o r d = R e c o r d . g e t P r e v R e c o r d ;  
 	 d a t a s e t . g e t N e x t R e c o r d = R e c o r d . g e t N e x t R e c o r d ;  
  
 	 d a t a s e t . g e t I d = D a t a s e t . p r o t o t y p e . g e t I d ;  
 	 d a t a s e t . i s F i r s t = D a t a s e t . p r o t o t y p e . i s F i r s t ;  
 	 d a t a s e t . i s L a s t =t = D a t a s e t . p r o t o t y p e . i s L a s t ;  
 	 d a t a s e t . i s A u t o L o a d P a g e = D a t a s e t . p r o t o t y p e . i s A u t o L o a d P a g e ;  
 	 d a t a s e t . g e t D e t a i l D a t a s e t s = D a t a s e t . p r o t o t y p e . g e t D e t a i l D a t a s e t s ;  
 	 d a t a s e t . g e t D i s a b l e C o n t r o l C o u n t = D a t a s e t . p r o t o t y p e . g e t D i s a b l e C o n t r o l C o u n t ;  
 	 d a t a s e t . g e t D i s a b l e E v e n t C o u n t = D a t a s e t . p r o t o t y p e . g e t D i s a b l e E v e n t C o u n t ;  
 	 d a t a s e t . g e t E d i t o r s = D a t a s e t . p r o t o t y p e . g e t E d i t o r s ;  
 	 d a t a s e t . f i e l d S e t = D a t a s e t . p r o t o t y p e . f i e l d S e t ;  
 	 d a t a s e t . i s M o d i f i e d = D a t a s e t . p r o t o t y p e . i s M o d i f i e d ;  
 	 d a t a s e t . g e t P a g e C o u n t = D a t a s e t . p r o t o t y p e . g e t P a g e C o u n t ;  
 	 d a t a s e t . g e t P a g e S i z e = D a t a s e t . p r o t o t y p e . g e t P a g e S i z e ;  
 	 d a t a s e t . s e t P a g e I n d e x = D a t a s e t . p r o t o t y p e . s e t P a g e I n d e x ;  
 	 d a t a s e t . g e t P a g e I n d e x = D a t a s e t . p r o t o t y p e . g e t P a g e I n d e x ;  
 	 d a t a s e t . g e t C u r r e n t = D a t a s e t . p r o t o t y p e . g e t C u r r e n t ;  
 	 d a t a s e t . s e t C u r r e n t = D a t a s e t . p r o t o t y p e . s e t C u r r e n t ;  
 	 d a t a s e t . g e t S t a t e = D a t a s e t . p r o t o t y p e . g e t S t a t e ;  
 	 d a t a s e t . g e t M a s t e r D a t a s e t = D a t a s e t . p r o t o t y p e . g e t M a s t e r D a t a s e t ;  
 	 d a t a s e t . g e t T a g = D a t a s e t . p r o t o t y p e . g e t T a g ;  
 	 d a t a s e t . s e t T a g = D a t a s e t . p r o t o t y p e . s e t T a g ;  
 	 d a t a s e t . g e t W i n d o w = D a t a s e t . p r o t o t y p e . g e t W i n d o w ;  
  
 	 d a t a s e t . g e t F i e l d = D a t a s e t . p r o t o t y p e . g e t F i e l d ;  
 	 d a t a s e t . g e t F i e l d C o u n t = D a t a s e t . p r o t o t y p e . g e t F i e l d C o u n t ;  
 	 d a t a s e t . g e t V a l u e = D a t a s e t . p r o t o t y p e . g e t V a l u e ;  
 	 d a t a s e t . g e t S t r i n g = D a t a s e t . p r o t o t y p e . g e t S t r i n g ;  
 	 d a t a s e t . s e t V a l u e = D a t a s e t . p r o t o t y p e . s e t V a l u e ;  
  
 	 d a t a s e t . d i s a b l e C o n t r o l s = D a t a s e t . p r o t o t y p e . d i s a b l e C o n t r o l s ;  
 	 d a t a s e t . e n a b l e C o n t r o l s = D a t a s e t . p r o t o t y p e . e n a b l e C o n t r o l s ;  
 	 d a t a s e t . d i s a b l e E v e n t s = D a t a s e t . p r o t o t y p e . d i s a b l e E v e n t s ;  
 	 d a t a s e t . e n a b l e E v e n t s = D a t a s e t . p r o t o t y p e . e n a b l e E v e n t s ;  
 	 d a t a s e t . r e f r e s h C o n t r o l s = D a t a s e t . p r o t o t y p e . r e f r e s h C o n t r o l s ;  
 	 d a t a s e t . s e t R e c o r d = D a t a s e t . p r o t o t y p e . s e t R e c o r d ;  
 	 d a t a s e t . s e t R e a d O n l y = D a t a s e t . p r o t o t y p e . s e t R e a d O n l y ;  
 	 d a t a s e t . s e t F i e l d R e a d O n l y = D a t a s e t . p r o t o t y p e . s e t F i e l d R e a d O n l y ;  
 	 d a t a s e t . g e t F i r s t R e c o r d = D a t a s e t . p r o t o t y p e . g e t F i r s t R e c o r d ;  
 	 d a t a s e t . g e t L a s t R e c o r d = D a t a s e t . p r o t o t y p e . g e t L a s t R e c o r d ;  
 	 d a t a s e t . m o v e = D a t a s e t . p r o t o t y p e . m o v e ;  
 	 d a t a s e t . m o v e P r e v = D a t a s e t . p r o t o t y p e . m o v e P r e v ;  
 	 d a t a s e t . m o v e N e x t = D a t a s e t . p r o t o t y p e . m o v e N e x t ;  
 	 d a t a s e t . m o v e F i r s t = D a t a s e t . p r o t o t y p e . m o v e F i r s t ;  
 	 d a t a s e t . m o v e L a s t = D a t a s e t . p r o t o t y p e . m o v e L a s t ;  
 	 d a t a s e t . f i n d = D a t a s e t . p r o t o t y p e . f i n d ;  
 	 d a t a s e t . l o c a t e = D a t a s e t . p r o t o t y p e . l o c a t e ;  
 	 / / d a t a s e t . p o s t R e c o r d = D a t a s e t . p r o t o t y p e . p o s t R e c o r d ;  
  
 	 d a t a s e t . i n s e r t R e c o r d = D a t a s e t . p r o t o t y p e . i n s e r t R e c o r d ;  
 	 d a t a s e t . d e l e t e R e c o r d = D a t a s e t . p r o t o t y p e . d e l e t e R e c o r d ;  
 	 d a t a s e t . c o p y R e c o r d = D a t a s e t . p r o t o t y p e . c o p y R e c o r d ;  
 	 d a t a s e t . l o a d P a g e = D a t a s e t . p r o t o t y p e . l o a d P a g e ;  
 	 d a t a s e t . l o a d D e t a i l = D a t a s e t . p r o t o t y p e . l o a d D e t a i l ;  
 	 d a t a s e t . i s P a g e L o a d e d = D a t a s e t . p r o t o t y p e . i s P a g e L o a d e d ;  
 	 d a t a s e t . m o v e T o P a g e = D a t a s e t . p r o t o t y p e . m o v e T o P a g e ;  
 	 d a t a s e t . s e t M a s t e r D a t a s e t = D a t a s e t . p r o t o t y p e . s e t M a s t e r D a t a s e t ;  
 	 d a t a s e t . f l u s h D a t a = D a t a s e t . p r o t o t y p e . f l u s h D a t a ;  
 	 d a t a s e t . c l e a r D a t a = D a t a s e t . p r o t o t y p e . c l e a r D a t a ;  
  
 	 d a t a s e t . p a r a m e t e r s = D a t a s e t . p r o t o t y p e . p a r a m e t e r s ;  
  
 	 d a t a s e t . g e t C o u n t = D a t a s e t . p r o t o t y p e . g e t C o u n t ;  
 	 d a t a s e t . s e l e c t A l l D a t a = D a t a s e t . p r o t o t y p e . s e l e c t A l l D a t a ;  
 	 d a t a s e t . r e l o a d D a t a = D a t a s e t . p r o t o t y p e . r e l o a d D a t a ;  
 	 d a t a s e t . f e t c h D a t a = D a t a s e t . p r o t o t y p e . f e t c h D a t a ;  
 	 d a t a s e t . g e t R e c o r d C o u n t = D a t a s e t . p r o t o t y p e . g e t R e c o r d C o u n t ;  
 	 d a t a s e t . s e t P o p u p E n a b l e = D a t a s e t . p r o t o t y p e . s e t P o p u p E n a b l e ;  
 	 d a t a s e t . s e t F i e l d P o p u p E n a b l e = D a t a s e t . p r o t o t y p e . s e t F i e l d P o p u p E n a b l e ;  
 	 d a t a s e t . i n s e r t R e c o r d W i t h V a l i d a t e = D a t a s e t . p r o t o t y p e . i n s e r t R e c o r d W i t h V a l i d a t e ;  
 	 d a t a s e t . v a l i d D a t a s e t = D a t a s e t . p r o t o t y p e . v a l i d D a t a s e t ;  
 	 d a t a s e t . c l e a r P a r a m e t e r s = D a t a s e t . p r o t o t y p e . c l e a r P a r a m e t e r s ;  
 	 d a t a s e t . s e t L o a d D a t a A c t i o n = D a t a s e t . p r o t o t y p e . s e t L o a d D a t a A c t i o n ;  
 	 d a t a s e t . s e t L o a d D a t a A c t i o n M e t h o d = D a t a s e t . p r o t o t y p e . s e t L o a d D a t a A c t i o n M e t h o d ;  
  
 	 d a t a s e t . _ s e t V a l u e = D a t a s e t . p r o t o t y p e . _ s e t V a l u e ;  
  
         d a t a s e t . p a g i n a t e F l u s h D a t a = D a t a s e t . p r o t o t y p e . p a g i n a t e F l u s h D a t a ;  
         d a t a s e t . i s F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . i s F i e l d M a s k C o n t r o l ;  
         d a t a s e t . r e f r e s h F i e l d M a s k C o n t r o l s = D a t a s e t . p r o t o t y p e . r e f r e s h F i e l d M a s k C o n t r o l s ;  
         d a t a s e t . j u d g e F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . j u d g e F i e l d M a s k C o n t r o l ;  
         d a t a s e t . c a l l F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . c a l l F i e l d M a s k C o n t r o l ;  
  
         i f   ( o p t . i d )   {  
         	  
                 d a t a s e t . i d   =   o p t . i d ;  
                 D o c u m e n t . a r r a y _ d a t a s e t [ D o c u m e n t . a r r a y _ d a t a s e t . l e n g t h ]   =   d a t a s e t ;  
         }  
         r e t u r n   d a t a s e t ;  
 }  
 / * *  
 f u n c t i o n   c r e a t e D a t a s e t ( I D )   {  
         v a r   d a t a s e t   =   n e w   p A r r a y ( ) ;  
  
         d a t a s e t . f i e l d s   =   [ ] ;  
         d a t a s e t . f i e l d s . c o u n t   =   D a t a s e t . f i e l d _ c o u n t ;  
         d a t a s e t . _ p a r a m e t e r s   =   n e w   P a r a m e t e r S e t ( ) ;  
  
         d a t a s e t . _ q u e r y F i e l d s   =   [ ] ; / /u(N嶵gRhz颫 ? 墎兎S講別pcn[Wk  
  
         d a t a s e t . u p d a t e I t e m s   =   [ ] ;  
         d a t a s e t . f i e l d s . f i e l d C o u n t   =   0 ;  
         d a t a s e t . p a g e S i z e   =   9 9 9 9 ;  
         d a t a s e t . p a g e C o u n t   =   1 ;  
         d a t a s e t . p a g e I n d e x   =   1 ;  
         d a t a s e t . r e c o r d C o u n t   =   0   ;  
         d a t a s e t . a u t o L o a d P a g e   =   f a l s e ;  
         d a t a s e t . d i s a b l e C o n t r o l C o u n t   =   0 ;  
         d a t a s e t . d i s a b l e E v e n t C o u n t   =   0 ;  
  
         d a t a s e t . m a s k C o n t r o l   =   f a l s e ;  
  
 	 d a t a s e t . a d d F i e l d = D a t a s e t . p r o t o t y p e . a d d F i e l d ;  
  
 	 d a t a s e t . g e t P r e v R e c o r d = R e c o r d . g e t P r e v R e c o r d ;  
 	 d a t a s e t . g e t N e x t R e c o r d = R e c o r d . g e t N e x t R e c o r d ;  
  
 	 d a t a s e t . g e t I d = D a t a s e t . p r o t o t y p e . g e t I d ;  
 	 d a t a s e t . i s F i r s t = D a t a s e t . p r o t o t y p e . i s F i r s t ;  
 	 d a t a s e t . i s L a s t = D a t a s e t . p r o t o t y p e . i s L a s t ;  
 	 d a t a s e t . i s A u t o L o a d P a g e = D a t a s e t . p r o t o t y p e . i s A u t o L o a d P a g e ;  
 	 d a t a s e t . g e t D e t a i l D a t a s e t s = D a t a s e t . p r o t o t y p e . g e t D e t a i l D a t a s e t s ;  
 	 d a t a s e t . g e t D i s a b l e C o n t r o l C o u n t = D a t a s e t . p r o t o t y p e . g  g e t D i s a b l e C o n t r o l C o u n t ;  
 	 d a t a s e t . g e t D i s a b l e E v e n t C o u n t = D a t a s e t . p r o t o t y p e . g e t D i s a b l e E v e n t C o u n t ;  
 	 d a t a s e t . g e t E d i t o r s = D a t a s e t . p r o t o t y p e . g e t E d i t o r s ;  
 	 d a t a s e t . f i e l d S e t = D a t a s e t . p r o t o t y p e . f i e l d S e t ;  
 	 d a t a s e t . i s M o d i f i e d = D a t a s e t . p r o t o t y p e . i s M o d i f i e d ;  
 	 d a t a s e t . g e t P a g e C o u n t = D a t a s e t . p r o t o t y p e . g e t P a g e C o u n t ;  
 	 d a t a s e t . g e t P a g e S i z e = D a t a s e t . p r o t o t y p e . g e t P a g e S i z e ;  
 	 d a t a s e t . s e t P a g e I n d e x = D a t a s e t . p r o t o t y p e . s e t P a g e I n d e x ;  
 	 d a t a s e t . g e t P a g e I n d e x = D a t a s e t . p r o t o t y p e . g e t P a g e I n d e x ;  
 	 d a t a s e t . g e t C u r r e n t = D a t a s e t . p r o t o t y p e . g e t C u r r e n t ;  
 	 d a t a s e t . s e t C u r r e n t = D a t a s e t . p r o t o t y p e . s e t C u r r e n t ;  
 	 d a t a s e t . g e t S t a t e = D a t a s e t . p r o t o t y p e . g e t S t a t e ;  
 	 d a t a s e t . g e t M a s t e r D a t a s e t = D a t a s e t . p r o t o t y p e . g e t M a s t e r D a t a s e t ;  
 	 d a t a s e t . g e t T a g = D a t a s e t . p r o t o t y p e . g e t T a g ;  
 	 d a t a s e t . s e t T a g = D a t a s e t . p r o t o t y p e . s e t T a g ;  
 	 d a t a s e t . g e t W i n d o w = D a t a s e t . p r o t o t y p e . g e t W i n d o w ;  
  
 	 d a t a s e t . g e t F i e l d = D a t a s e t . p r o t o t y p e . g e t F i e l d ;  
 	 d a t a s e t . g e t F i e l d C o u n t = D a t a s e t . p r o t o t y p e . g e t F i e l d C o u n t ;  
 	 d a t a s e t . g e t V a l u e = D a t a s e t . p r o t o t y p e . g e t V a l u e ;  
 	 d a t a s e t . g e t S t r i n g = D a t a s e t . p r o t o t y p e . g e t S t r i n g ;  
 	 d a t a s e t . s e t V a l u e = D a t a s e t . p r o t o t y p e . s e t V a l u e ;  
  
 	 d a t a s e t . d i s a b l e C o n t r o l s = D a t a s e t . p r o t o t y p e . d i s a b l e C o n t r o l s ;  
 	 d a t a s e t . e n a b l e C o n t r o l s = D a t a s e t . p r o t o t y p e . e n a b l e C o n t r o l s ;  
 	 d a t a s e t . d i s a b l e E v e n t s = D a t a s e t . p r o t o t y p e . d i s a b l e E v e n t s ;  
 	 d a t a s e t . e n a b l e E v e n t s = D a t a s e t . p r o t o t y p e . e n a b l e E v e n t s ;  
 	 d a t a s e t . r e f r e s h C o n t r o l s = D a t a s e t . p r o t o t y p e . r e f r e s h C o n t r o l s ;  
 	 d a t a s e t . s e t R e c o r d = D a t a s e t . p r o t o t y p e . s e t R e c o r d ;  
 	 d a t a s e t . s e t R e a d O n l y = D a t a s e t . p r o t o t y p e . s e t R e a d O n l y ;  
 	 d a t a s e t . s e t F i e l d R e a d O n l y = D a t a s e t . p r o t o t y p e . s e t F i e l d R e a d O n l y ;  
 	 d a t a s e t . g e t F i r s t R e c o r d = D a t a s e t . p r o t o t y p e . g e t F i r s t R e c o r d ;  
 	 d a t a s e t . g e t L a s t R e c o r d = D a t a s e t . p r o t o t y p e . g e t L a s t R e c o r d ;  
 	 d a t a s e t . m o v e = D a t a s e t . p r o t o t y p e . m o v e ;  
 	 d a t a s e t . m o v e P r e v = D a t a s e t . p r o t o t y p e . m o v e P r e v ;  
 	 d a t a s e t . m o v e N e x t = D a t a s e t . p r o t o t y p e . m o v e N e x t ;  
 	 d a t a s e t . m o v e F i r s t = D a t a s e t . p r o t o t y p e . m o v e F i r s t ;  
 	 d a t a s e t . m o v e L a s t = D a t a s e t . p r o t o t y p e . m o v e L a s t ;  
 	 d a t a s e t . f i n d = D a t a s e t . p r o t o t y p e . f i n d ;  
 	 d a t a s e t . l o c a t e = D a t a s e t . p r o t o t y p e . l o c a t e ;  
 	 / / d a t a s e t . p o s t R e c o r d = D a t a s e t . p r o t o t y p e . p o s t R e c o r d ;  
  
 	 d a t a s e t . i n s e r t R e c o r d = D a t a s e t . p r o t o t y p e . i n s e r t R e c o r d ;  
 	 d a t a s e t . d e l e t e R e c o r d = D a t a s e t . p r o t o t y p e . d e l e t e R e c o r d ;  
 	 d a t a s e t . c o p y R e c o r d = D a t a s e t . p r o t o t y p e . c o p y R e c o r d ;  
 	 d a t a s e t . l o a d P a g e = D a t a s e t . p r o t o t y p e . l o a d P a g e ;  
 	 d a t a s e t . l o a d D e t a i l = D a t a s e t . p r o t o t y p e . l o a d D e t a i l ;  
 	 d a t a s e t . i s P a g e L o a d e d = D a t a s e t . p r o t o t y p e . i s P a g e L o a d e d ;  
 	 d a t a s e t . m o v e T o P a g e = D a t a s e t . p r o t o t y p e . m o v e T o P a g e ;  
 	 d a t a s e t . s e t M a s t e r D a t a s e t = D a t a s e t . p r o t o t y p e . s e t M a s t e r D a t a s e t ;  
 	 d a t a s e t . f l u s h D a t a = D a t a s e t . p r o t o t y p e . f l u s h D a t a ;  
 	 d a t a s e t . c l e a r D a t a = D a t a s e t . p r o t o t y p e . c l e a r D a t a ;  
  
 	 d a t a s e t . p a r a m e t e r s = D a t a s e t . p r o t o t y p e . p a r a m e t e r s ;  
  
 	 d a t a s e t . g e t C o u n t = D a t a s e t . p r o t o t y p e . g e t C o u n t ;  
 	 d a t a s e t . s e l e c t A l l D a t a = D a t a s e t . p r o t o t y p e . s e l e c t A l l D a t a ;  
 	 d a t a s e t . r e l o a d D a t a = D a t a s e t . p r o t o t y p e . r e l o a d D a t a ;  
 	 d a t a s e t . f e t c h D a t a = D a t a s e t . p r o t o t y p e . f e t c h D a t a ;  
 	 d a t a s e t . g e t R e c o r d C o u n t = D a t a s e t . p r o t o t y p e . g e t R e c o r d C o u n t ;  
 	 d a t a s e t . s e t P o p u p E n a b l e = D a t a s e t . p r o t o t y p e . s e t P o p u p E n a b l e ;  
 	 d a t a s e t . s e t F i e l d P o p u p E n a b l e = D a t a s e t . p r o t o t y p e . s e t F i e l d P o p u p E n a b l e ;  
 	 d a t a s e t . i n s e r t R e c o r d W i t h V a l i d a t e = D a t a s e t . p r o t o t y p e . i n s e r t R e c o r d W i t h V a l i d a t e ;  
 	 d a t a s e t . v a l i d D a t a s e t = D a t a s e t . p r o t o t y p e . v a l i d D a t a s e t ;  
 	 d a t a s e t . c l e a r P a r a m e t e r s = D a t a s e t . p r o t o t y p e . c l e a r P a r a m e t e r s ;  
 	 d a t a s e t . s e t L o a d D a t a A c t i o n = D a t a s e t . p r o t o t y p e . s e t L o a d D a t a A c t i o n ;  
 	 d a t a s e t . s e t L o a d D a t a A c t i o n M e t h o d = D a t a s e t . p r o t o t y p e . s e t L o a d D a t a A c t i o n M e t h o d ;  
  
 	 d a t a s e t . _ s e t V a l u e = D a t a s e t . p r o t o t y p e . _ s e t V a l u e ;  
  
         d a t a s e t . p a g i n a t e F l u s h D a t a = D a t a s e t . p r o t o t y p e . p a g i n a t e F l u s h D a t a ;  
         d a t a s e t . i s F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . i s F i e l d M a s k C o n t r o l ;  
         d a t a s e t . r e f r e s h F i e l d M a s k C o n t r o l s = D a t a s e t . p r o t o t y p e . r e f r e s h F i e l d M a s k C o n t r o l s ;  
         d a t a s e t . j u d g e F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . j u d g e F i e l d M a s k C o n t r o l ;  
         d a t a s e t . c a l l F i e l d M a s k C o n t r o l = D a t a s e t . p r o t o t y p e . c a l l F i e l d M a s k C o n t r o l ;  
  
         i f   ( I D )   {  
                 d a t a s e t . i d   =   I D ;  
                 D o c u m e n t . a r r a y _ d a t a s e t [ D o c u m e n t . a r r a y _ d a t a s e t . l e n g t h ]   =   d a t a s e t ;  
         }  
         r e t u r n   d a t a s e t ;  
 }  
  
 * /  
  
 / /?徢 Q u e r y C o m m a n d[鶎a兎S X M Lepcn^v\epcnXkQER0 D a t a s e tN-  
 f u n c t i o n   f i l l D a t a s e t ( d a t a s e t   ,   m a s t e r D a t a s e t ) {  
 	 i f   ( d a t a s e t . p r e p a r e d )   {  
         r e t u r n ;  
     }  
     d a t a s e t . w i n d o w   =   w i n d o w ;  
     d a t a s e t . _ b o f   =   t r u e ;  
     d a t a s e t . _ e o f   =   t r u e ; ;  
     d a t a s e t . s t a t e   =   " n o n e " ;  
     d a t a s e t . r e a d O n l y   =   S y s t e m . i s T r u e ( d a t a s e t . r e a d O n l y ) ;  
  
     d a t a s e t . l o a d e d D e t a i l   =   [ ] ;  
     d a t a s e t . l o a d e d P a g e   =   [ ] ;  
  
     i f   ( d a t a s e t . p a g e I n d e x   >   0 )   {  
             d a t a s e t . l o a d e d P a g e [ d a t a s e t . p a g e I n d e x   -   1 ]   =   t r u e ;  
     }  
  
     i f (   d a t a s e t . p a g i n a t e   ) {  
 	 	 / /_SRMv剺uep  
 	 	 v a r 	 p a g e I n d e x   =   d a t a s e t . p a g e I n d e x ;  
 	 	 i f (   ! p a g e I n d e x   ) {  
 	 	 	 p a g e I n d e x   =   1 ;  
 	 	 }  
 	 	 / /兎S謐螻 榰v刌'\  
 	 	 v a r   p a g e S i z e   =   d a t a s e t . p a g e S i z e ;  
 	 	 i f (   ! p a g e S i z e   ) {  
 	 	 	 p a g e S i z e   =   0 ;  
 	 	 }  
 	 	 / /\榰epO\N:S耬pO ?~賕Rhz  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e I n d e x " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e S i z e " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e I n d e x " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e I n d e x " , p a g e I n d e x ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e S i z e " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e S i z e " , p a g e S i z e ) ;  
 	 }  
  
     / /R^ Q u e r y C o m m a n d[鶎a^v?徢 Q u e r y C o m m a n d[鶎abg圠g鍕鈊蚈\  
     i f (   G l o b a l . o n S e r v e r   & &   ! d a t a s e t . s t a t i c D a t a S o u r c e   & &   d a t a s e t . l o a d D a t a A c t i o n ! = u n d e f i n e d ) { / /Y俫渇/W(gRhz飶袌LR兎S謌Rhz飗別pcn0  
 	 	 v a r   c m d   =   n e w   Q u e r y C o m m a n d ( ) ;  
 	 	 c m d . s e t D a t a s e t (   d a t a s e t   )   ; / /\ d a t a s a s e t~腘鯫 ?徾 c m d[鶎aW( c m dv刅迣Q齟pN-[ d a t a s e t徾圠RY薙  
 	 	 c m d . e x e c u t e ( ) ; / /bg圠g鍕  
 	 }  
 	 e l s e  
 	 {  
 	 	 e v a l ( " v a r   i s X m l E x i s t = ( t y p e o f ( _ _ "   +   d a t a s e t . i d   +   " ) = = \ " o b j e c t \ " ) " ) ;  
 	 	 i f   ( i s X m l E x i s t )   {  
 	 	 	 e v a l ( " v a r   x m l I s l a n d = _ _ "   +   d a t a s e t . i d ) ; / /兎S謽u梑N
v X M Lepcn\?墎lBW(榰梑N
_艠{c蠴汵 N*zzv X M Lepcn\? i dTy餘: " _ _ "   +   d a t a s e tv I D  
 	 	 }  
 	 	 e l s e {  
 	 	     i f ( d a t a s e t . x m l F o r m a t   = =   " i t e m s " ) {  
 	 	         v a r   d r o p d o w n O b j e c t s   =   e v a l ( " _ _ _ " + d a t a s e t . i d ) ;  
 	 	     }  
 	 	 }  
  
 	 	 i f (   ( t y p e o f ( x m l I s l a n d ) = = " u n d e f i n e d "   | |   x m l I s l a n d = = n u l l )  
 	 	   & &   ( t y p e o f ( d r o p d o w n O b j e c t s ) = = " u n d e f i n e d "   | |   d r o p d o w n O b j e c t s = = n u l l )   )  
 	 	     r e t u r n ;  
  
 	 	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	 	 i f   ( d a t a s e t . x m l F o r m a t   = =   " r e c o r d s "   & &   i s X m l E x i s t )   { / /Y俫渇/ < r e c o r d s >h<_v X M L  
 	 	 	 v a r   c u r r e n t   =   D a t a s e t . a p p e n d F r o m X m l ( d a t a s e t ,   x m l I s l a n d . d o c u m e n t E l e m e n t ,   t r u e ) ;  
 	 	 	 d a t a s e t . p r e p a r e d   =   t r u e ;  
 	   	 } e l s e   i f   ( d a t a s e t . x m l F o r m a t   = =   " i t e m s " )   { / /Y俫渇/ < i t e m s >h<_v X M L  
 	 	 	 / /Y俫渇/[7z?v鬰 x m l  
 	 	 	 i f ( t y p e o f ( d r o p d o w n O b j e c t s ) ! = " u n d e f i n e d "   & &   d r o p d o w n O b j e c t s ! = n u l l ) {  
 	 	 	     f o r ( v a r   i = 0 ;   i < d r o p d o w n O b j e c t s . l e n g t h ;   i + + ) {  
 	 	 	         v a r   r e c o r d   =   d r o p d o w n O b j e c t s [ i ] ;  
 	 	 	         d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   r e l l ,   r e c o r d ) ;  
 	 	 	         i n i t R e c o r d ( r e c o r d ,   d a t a s e t ) ;  
 	 	 	     }  
 	 	 	 }  
 	 	 	 e l s e {  
 	 	 	 	 r o o t = x m l I s l a n d . d o c u m e n t E l e m e n t ;  
 	 	 	 	 i f   ( r o o t )   {  
 	 	 	 	 	 v a r   i t e m N o d e s   =   r o o t . c h i l d N o d e s ;  
 	 	 	 	 	 f o r   ( v a r   i = 0 ;   i < i t e m N o d e s . l e n g t h ;   i + + )   {  
 	 	 	 	 	 	  
 	 	 	 	 	 	 v a r   i t e m N o d e   =   i t e m N o d e s . i t e m ( i ) ;  
 	 	 	 	 	 	  
 	 	 	 	 	 	 v a r   v   =   i t e m N o d e . g e t A t t r i b u t e ( " v a l u e " )   | |   i t e m N o d e . g e t A t t r i b u t e ( " v " )   ;  
 	 	 	 	 	 	 v a r   l   =   i t e m N o d e . g e t A t t r i b u t e ( " l a b e l " )   | |   i t e m N o d e . g e t A t t r i b u t e ( " l " )   ;  
 	 	 	 	 	 	 v a r   v i   =   i t e m N o d e . g e t A t t r i b u t e ( " v a l u e I d " )   | |   i t e m N o d e . g e t A t t r i b u t e ( " v i " )   ;  
 	 	 	 	 	 	  
 	 	 	 	 	 	 v a r   r e c o r d   =   [ v , v i , l ] ;  
 	 	 	 	 	 	 d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   r e c o r d ) ;  
 	 	 	 	 	 	 i n i t R e c o r d ( r e c o r d ,   d a t a s e t ) ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 }  
 	 	 }  
 	 	 / /懯e>Q匸X  
 	 	 i f (   ! ( t y p e o f ( x m l I s l a n d ) = = " u n d e f i n e d "   | |   x m l I s l a n d = = n u l l )   )  
 	 	 {  
 	 	 	 i f ( x m l I s l a n d . r e m o v e N o d e )  
 	 	 	 	 x m l I s l a n d . r e m o v e N o d e ( t r u e ) ;  
 	 	 	 x m l I s l a n d   =   n u l l ;  
 	 	 }  
 	 	 i f   ( c u r r e n t )   {  
 	 	 	 d a t a s e t . s e t R e c o r d ( c u r r e n t ) ;  
 	 	 }   e l s e   {  
 	 	 	 i f   ( d a t a s e t . p a g e I n d e x   = =   1   | |   ! d a t a s e t . a u t o L o a d P a g e )   {  
 	 	 	 	 d a t a s e t . m o v e F i r s t ( ) ;  
 	 	 	 }   e l s e   {  
 	 	 	 	 d a t a s e t . s e t R e c o r d ( d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 	 }  
 	 	 }  
  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
  
 	 }  
 	  
 	 i f   ( m a s t e r D a t a s e t )   {  
 	               d a t a s e t . s e t M a s t e r D a t a s e t ( d a t a s e t . m a s t e r D a t a s e t ,   d a t a s e t . m a s t e r K e y F i e l d s ,   d a t a s e t . d e t a i l K e y F i e l d s ) ;  
 	 }  
 }  
 / /[ f i l l D a t a s e t  Q齟p{€Q  
 f u n c t i o n   f d ( d a t a s e t   ,   m a s t e r D a t a s e t ) {  
 	 f i l l D a t a s e t ( d a t a s e t   ,   m a s t e r D a t a s e t )   ;  
 }  
 v a r   D a t a s e t   =   C l a s s . c r e a t e ( ) ;  
  
 D a t a s e t . p r o t o t y p e   =   {  
  
 g e t I d : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . i d ;  
 } ,  
  
 i s F i r s t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . _ b o f ;  
 } ,  
  
 i s L a s t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . _ e o f ;  
 } ,  
  
 i s A u t o L o a d P a g e : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . a u t o L o a d P a g e ;  
 } ,  
  
 g e t D e t a i l D a t a s e t s : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . d e t a i l D a t a s e t s ;  
 } ,  
  
 g e t D i s a b l e C o n t r o l C o u n t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . d i s a b l e C o n t r o l C o u n t ;  
 } ,  
  
 g e t D i s a b l e E v e n t C o u n t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . d i s a b l e E v e n t C o u n t ;  
 } ,  
  
 g e t E d i t o r s : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . e d i t o r s ;  
 } ,  
  
 f i e l d S e t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . f i e l d s ;  
 } ,  
  
 i s M o d i f i e d : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . m o d i f i e d ;  
 } ,  
  
 g e t P a g e C o u n t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . . p a g e C o u n t ;  
 } ,  
  
 / * *  
徺N*Q齟pW(R榰v刞臦礜Ou( ,坔y:{&Tg鍕鈍aN鰒剫癬Uv刞;ep ,€Nf/_SRM  
 D a t a s e t懱梑v剫癬U`;ep ,W(R榰v刞臦礜 ,epcn^搼虠b{&Tg鍕鈍aN鰒剫癬US飥  
g	 1 0 0 0 0ga ,OFf/Y俫淸歂I D a t a s e tv p a g e S i z eN: 3 0v剫 , D a t a s e t懱Qv[濻阦	 3 0N*嫲  
_U ,徺N*e筶諒訴辷凬f/ D a t a s e t懱_SRMv剫癬Uep ,€f/epcn^搼蘽&Tg鍕鈍aN鰒  
嫲_Uep  
 * /  
 g e t R e c o r d C o u n t : f u n c t i o n ( ) {  
 	 r e t u r n   t h i s . r e c o r d C o u n t ;  
 } ,  
  
 / *  
 A d d   2 0 0 6 - 0 4 - 2 0  
徺N*Q齟p徳V轤SRM D a t a s e t懱v剫癬U`;ep .  
 * /  
 g e t C o u n t : f u n c t i o n ( ) {  
         v a r   c o u n t   =   0 ;  
 	 v a r   r e c o r d = t h i s . f i r s t U n i t ;  
 	 i f ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) )   c o u n t + + ;  
 	 w h i l e   ( r e c o r d ) {  
 	 	 r e c o r d = r e c o r d . n e x t U n i t ;  
 	 	 i f   ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) )   c o u n t + + ;  
 	 }  
 	 r e t u r n   c o u n t ;  
 } ,  
  
 g e t P a g e S i z e : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . p a g e S i z e ;  
 } ,  
  
 s e t P a g e I n d e x : f u n c t i o n   ( p a g e I n d e x )   {  
         t h i s . p a g e I n d e x   =   p a g e I n d e x ;  
 } ,  
  
 s e t A s y n c : f u n c t i o n (   a s y n c   ) {  
 	 t h i s . a s y n c   =   a s y n c   ;  
 } ,  
  
 g e t A s y n c : f u n c t i o n ( ) {  
 	 r e t u r n   t h i s . a s y n c   ;  
 } ,  
  
 g e t P a g e I n d e x : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . p a g e I n d e x ;  
 } ,  
  
 g e t C u r r e n t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . r e c o r d ;  
 } ,  
  
 s e t C u r r e n t : f u n c t i o n   ( r e c o r d )   {  
         r e c o r d . d a t a s e t . s e t R e c o r d ( r e c o r d ) ;  
 } ,  
  
 g e t S t a t e : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . s t a t e ;  
 } ,  
  
 g e t M a s t e r D a t a t a s e t : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . m a s t e r D a t a s e t ;  
 } ,  
  
 g e t T a g : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . t a g ;  
 } ,  
  
 s e t T a g : f u n c t i o n   ( t a g )   {  
         t h i s . t a g   =   t a g ;  
 } ,  
  
 g e t W i n d o w : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . w i n d o w ;  
 } ,  
  
 g e t L o a d D a t a A c t i o n : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . l o a d D a t a A c t i o n ;  
 } ,  
  
 s e t L o a d D a t a A c t i o n : f u n c t i o n   ( l o a d D a t a A c t i o n )   {  
         t h i s . l o a d D a t a A c t i o n   =   l o a d D a t a A c t i o n ;  
 } ,  
  
 g e t L o a d D a t a A c t i o n M e t h o d : f u n c t i o n ( ) {  
 	 r e t u r n   t h i s . l o a d D a t a A c t i o n M e t h o d ;  
 } ,  
  
 s e t L o a d D a t a A c t i o n M e t h o d : f u n c t i o n (   l o a d D a t a A c t i o n M e t h o d   ) {  
 	 t h i s . l o a d D a t a A c t i o n M e t h o d   =   l o a d D a t a A c t i o n M e t h o d   ;  
 } ,  
  
 g e t F i e l d : f u n c t i o n   ( n a m e )   {  
         v a r   d a t a s e t   =   t h i s ;  
         r e t u r n   D a t a s e t . g e t F i e l d ( d a t a s e t . f i e l d s ,   n a m e ) ;  
 } ,  
  
 g e t F i e l d C o u n t : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         r e t u r n   d a t a s e t . f i e l d s . c o u n t ( ) ;  
 } ,  
  
 g e t V a l u e : f u n c t i o n   ( f i e l d N a m e )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( d a t a s e t . r e c o r d )   {  
                 r e t u r n   d a t a s e t . r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
         }   e l s e   {  
                 r e t u r n   " " ;  
         }  
 } ,  
  
 g e t S t r i n g : f u n c t i o n   ( f i e l d N a m e )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( d a t a s e t . r e c o r d )   {  
                 r e t u r n   d a t a s e t . r e c o r d . g e t S t r i n g ( f i e l d N a m e ) ;  
         }   e l s e   {  
                 r e t u r n   " " ;  
         }  
 } ,  
  
 / /~ D a t a s e tv刜SRM嫲_Uv刢[歷刐Wk礣v刐Wk祴?nP<0  
 _ s e t V a l u e : f u n c t i o n   ( f i e l d N a m e ,   v a l u e )   {  
  
                 v a r   d a t a s e t   =   t h i s ;  
                 i f   ( ! d a t a s e t . r e c o r d )   {  
                 	 v a r   n e w R e c o r d = [ ] ;  
 	 	         / /W( D a t a s e tv剶h~觛凬-v刜SRM嫲_Uc襋eN N*嫲_U  
 	 	         d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   n e w R e c o r d ) ;  
 	 	         i n i t R e c o r d ( n e w R e c o r d ,   d a t a s e t ) ;  
 	 	         d a t a s e t . r e c o r d   =   n e w R e c o r d ;  
                 }  
                 i f   ( d a t a s e t . r e c o r d )   {  
                         d a t a s e t . r e c o r d . _ s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
                 }   e l s e   {  
                         t h r o w   C o n s t . E r r N o C u r r e n t R e c o r d ;  
                 }  
  
 } ,  
  
 / /~ D a t a s e tv刜SRM嫲_Uv刢[歷刐Wk礣v刐Wk祴?nP<0  
 s e t V a l u e : f u n c t i o n   ( f i e l d N a m e ,   v a l u e )   {  
  
                 v a r   d a t a s e t   =   t h i s ;  
                 i f   ( ! d a t a s e t . r e c o r d )   {  
                 	 v a r   n e w R e c o r d = [ ] ;  
 	 	         / /W( D a t a s e tv剶h~觛凬-v刜SRM嫲_Uc襋eN N*嫲_U  
 	 	         d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   n e w R e c o r d ) ;  
 	 	         i n i t R e c o r d ( n e w R e c o r d ,   d a t a s e t ) ;  
 	 	         d a t a s e t . r e c o r d   =   n e w R e c o r d ;  
                 }  
                 i f   ( d a t a s e t . r e c o r d )   {  
                         d a t a s e t . r e c o r d . s e t V a l u e ( f i e i e l d N a m e ,   v a l u e ) ;  
                 }   e l s e   {  
                         t h r o w   C o n s t . E r r N o C u r r e n t R e c o r d ;  
                 }  
  
 } ,  
  
 d i s a b l e C o n t r o l s : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         d a t a s e t . d i s a b l e C o n t r o l C o u n t   =   d a t a s e t . d i s a b l e C o n t r o l C o u n t   +   1 ;  
 } ,  
  
 e n a b l e C o n t r o l s : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         d a t a s e t . d i s a b l e C o n t r o l C o u n t   =   ( d a t a s e t . d i s a b l e C o n t r o l C o u n t   >   0 )   ?   d a t a s e t . d i s a b l e C o n t r o l C o u n t   -   1   :   0 ;  
         d a t a s e t . r e f r e s h C o n t r o l s ( ) ;  
 } ,  
  
 d i s a b l e E v e n t s : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         d a t a s e t . d i s a b l e E v e n t C o u n t   =   d a t a s e t . d i s a b l e E v e n t C o u n t   +   1 ;  
 } ,  
  
 e n a b l e E v e n t s : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         d a t a s e t . d i s a b l e E v e n t C o u n t   =   ( d a t a s e t . d i s a b l e E v e n t C o u n t   >   0 )   ?   d a t a s e t . d i s a b l e E v e n t C o u n t   -   1   :   0 ;  
 } ,  
  
 r e f r e s h C o n t r o l s : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         / /榰梑RY薙[宬誖M梌Nb蒖╜epcn柶Nu(R7e  
         i f ( ! C o n t r o l . d o c u m e n t I n i t i a l i z e d   & &   ! d a t a s e t . s t a t i c D a t a S o u r c e   & &   d a t a s e t . x m l F o r m a t = = " r e c o r d s " )  
             r e t u r n ;  
              
         D a t a s e t . v a l i d a t e C u r s o r ( d a t a s e t ) ;  
         d a t a s e t . l o a d D e t a i l ( ) ;  
         / / v a r   s t a r t D a t e F   =   n e w   D a t e ( ) ;  
          D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y R e f r e s h ,   d a t a s e t ) ;  
    
 	 / / j s D e b u g . d e b u g ( " . . 3 3 3 3 3 3 3 3 3 3 . . . . . .   :     " + d a t a s e t . i d + "   " + ( ( n e w   D a t e ( ) ) - s t a r t D a t e F ) ) ;  
 } ,  
  
 s e t R e c o r d : f u n c t i o n   ( r e c o r d )   {  
  
         D a t a s e t . s e t R e c o r d ( t h i s ,   r e c o r d ) ;  
  
 } ,  
  
 s e t R e a d O n l y : f u n c t i o n   ( r e a d O n l y )   {  
         v a r   d a t a s e t   =   t h i s ;  
         d a t a s e t . r e a d O n l y   =   r e a d O n l y ;  
         f o r ( v a r   i = 0 ;   i < d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
                 i f ( d a t a s e t . i s F i e l d M a s k C o n t r o l ( d a t a s e t . f i e l d s [ i ] . g e t N a m e ( ) ) ) {  
                     	 d a t a s e t . f i e l d s [ i ] . r e a d O n l y   =   t r u e ;  
                 } e l s e {  
 	                 i f ( C o n t r o l . b e f o r e _ P a g e _ o n L o a d ) {  
 	                     	 i f ( ! d a t a s e t . f i e l d s [ i ] . r e a d O n l y ) {  
 	                         	 d a t a s e t . f i e l d s [ i ] . r e a d O n l y   =   r e a d O n l y ;  
 	                     	 }  
 	                 }  
 	                 e l s e {  
 	                     	 d a t a s e t . f i e l d s [ i ] . r e a d O n l y   =   r e a d O n l y ;  
 	                 }  
                 }  
         }  
         D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ) ;  
 } ,  
  
 r e f r e s h F i e l d M a s k C o n t r o l s : f u n c t i o n ( ) {  
 	 v a r   d a t a s e t   =   t h i s ;  
 	 i f ( d a t a s e t . m a s k C o n t r o l   & &   d a t a s e t . m a s k C o n t r o l F i e l d s ) {  
 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . m a s k C o n t r o l F i e l d s . l e n g t h ;   i + + ) {  
 	 	 	 d a t a s e t . s e t F i e l d P o p u p E n a b l e ( d a t a s e t . m a s k C o n t r o l F i e l d s [ i ] ,   f a l s e ) ;  
 	 	 }  
 	 }  
 } ,  
  
 i s F i e l d M a s k C o n t r o l : f u n c t i o n ( f i e l d N a m e ) {  
 	 v a r   r e s u l t   =   f a l s e ;  
 	 v a r   d a t a s e t   =   t h i s ;  
 	 i f ( d a t a s e t . m a s k C o n t r o l   & &   d a t a s e t . m a s k C o n t r o l F i e l d s ) {  
 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . m a s k C o n t r o l F i e l d s . l e n g t h ;   i + + ) {  
 	 	 	 i f ( f i e l d N a m e = = d a t a s e t . m a s k C o n t r o l F i e l d s [ i ] ) {  
 	 	 	 	 r e s u l t   =   t r u e ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
 	 }  
 	 r e t u r n   r e s u l t ;  
 } ,  
  
 j u d g e F i e l d M a s k C o n t r o l : f u n c t i o n ( ) {  
 	 v a r   r e s u l t   =   t r u e ;  
 	 v a r   d a t a s e t   =   t h i s ;  
 	 i f ( d a t a s e t . m a s k C o n t r o l   & &   d a t a s e t . m a s k C o n t r o l F i e l d s   & &   d a t a s e t . m a s k C o n t r o l F i e l d s . l e n g t h > 0 ) {  
 	 	 d a t a s e t . s e t R e a d O n l y ( t r u e ) ;  
 	 	 d a t a s e t . s e t P o p u p E n a b l e ( t r u e ) ;  
 	 	 a l e r t ( "[鵑峸`╲別pcngC朠N嵆 ,e鄉誨蚈\嫲_U0 " ) ;  
 	 	 r e s u l t   =   f a l s e ;  
 	 }  
 	 r e t u r n   r e s u l t ;  
 } ,  
  
 c a l l F i e l d M a s k C o n t r o l : f u n c t i o n ( a s y n c ,   c a l l b a c k ) {  
 	 v a r   d a t a s e t   =   t h i s ;  
 	 v a r   a j a x C a l l F o r M a s k F i e l d s   =   n e w   N D A j a x C a l l ( a s y n c ) ;  
 	 a j a x C a l l F o r M a s k F i e l d s . r e m o t e C a l l ( " P r i v i l e g e S e r v i c e " ,   " g e t C u s t C t r l D a t a I n f o " ,   [ G l o b a l . p a g e I d ,   d a t a s e t . i d ,   G l o b a l . c u s t T y p e I d ] ,   f u n c t i o n ( r e p l y ) {  
 	 	 d a t a s e t . m a s k C o n t r o l F i e l d s   =   r e p l y . g e t R e s u l t ( ) ;  
 	 	 / / d a t a s e t . m a s k C o n t r o l F i e l d s   =   [ " c u s t N a m e " ,   " c u s t D e p t T y p e " ,   " t y p e F l a g " ,   " c o n t a c t N a m e " , " a c c t I d " ,   " c u s t L o c a t i o n " ,   " i m p o r t a n c e L e v e l " ,   " l a t e n t V i p F l a g " ,   " c e r t i f i c a t e N o " ] ;  
 	 	 d a t a s e t . r e f r e s h F i e l d M a s k C o n t r o l s ( ) ;  
 	 	 i f ( c a l l b a c k )  
 	 	 	 c a l l b a c k ( ) ;  
 	 } ) ;  
 } ,  
  
 s e t F i e l d R e a d O n l y : f u n c t i o n   ( f i e l d N a m e ,   r e a d O n l y )   {  
         v a r   d a t a s e t   =   t h i s ;  
         v a r   f i e l d   =   d a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
         i f   ( f i e l d )   {  
         	 i f ( d a t a s e t . i s F i e l d M a s k C o n t r o l ( f i e l d N a m e ) ) {  
                     	 d a t a s e t . f i e l d s [ i ] . r e a d O n l y   =   t r u e ;  
                 }  
                 e l s e {  
                 	 f i e l d . r e a d O n l y   =   r e a d O n l y ;  
                 }  
                 D a t a s e t . b r o a d c a s t F i e l d M s g ( D a t a s e t . n o t i f y F i e l d S t a t e C h a n g e d ,   d a t a s e t ,   d a t a s e t . r e c o r d ,   f i e l d ) ;  
         }   e l s e   {  
                 t h r o w   C o n s t . E r r C a n t F i n d F i e l d . r e p l a c e ( " % s " ,   d a t a s e t . i d   +   " . "   +   f i e l d N a m e ) ;  
         }  
 } ,  
  
 s e t P o p u p E n a b l e : f u n c t i o n   ( e n a b l e )   {  
         v a r   d a t a s e t   =   t h i s ;  
         f o r ( v a r   i = 0 ;   i < d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
             v a r   f i e l d   =   d a t a s e t . f i e l d s [ i ] ;  
             d a t a s e t . s e t F i e l d P o p u p E n a b l e ( f i e l d . g e t N a m e ( ) ,   e n a b l e ) ;  
         }  
 } ,  
  
 s e t F i e l d P o p u p E n a b l e : f u n c t i o n   ( f i e l d N a m e ,   e n a b l e )   {  
         v a r   d a t a s e t   =   t h i s ;  
         v a r   f i e l d   =   d a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
         i f   ( f i e l d )   {  
         	 i f ( e n a b l e ) {  
         	 	 i f ( d a t a s e t . i s F i e l d M a s k C o n t r o l ( f i e l d N a m e ) )  
         	 	         r e t u r n ;  
         	 }  
                 v a r   p o p u p   =   $ ( " b u t t o n _ " + d a t a s e t . i d + " _ " + f i e l d N a m e ) ;  
                 i f ( p o p u p ) {  
                         p o p u p . d i s a b l e d   =   ! S y s t e m . i s T r u e ( e n a b l e ) ;  
                         i f ( p o p u p . c l a s s N a m e ! = " c o o l B u t t o n 2 " )  
                         	 p o p u p . c l a s s N a m e   =   ( S y s t e m . i s T r u e ( e n a b l e )   ?   " p o p u p B u t t o n "   :   " p o p u p B u t t o n _ d i s a b l e " ) ;  
                 }  
                 v a r   r e s e t   =   $ ( " b u t t o n _ r e s e t _ " + d a t a s e t . i d + " _ " + f i e l d N a m e ) ;  
                 i f ( r e s e t ) {  
                         r e s e t . d i s a b l e d   =   ! S y s t e m . i s T r u e ( e n a b l e ) ;  
                         i f ( r e s e t . c l a s s N a m e ! = " c o o l B u t t o n 2 " )  
                         	 r e s e t . c l a s s N a m e   =   ( S y s t e m . i s T r u e ( e n a b l e )   ?   " r e s e t B u t t o n "   :   " r e s e t B u t t o n _ d i s a b l e " ) ;  
                 }  
         }  
 } ,  
  
 g e t F i r s t R e c o r d : f u n c t i o n   ( )   {  
         r e t u r n   D a t a s e t . g e t F i r s t R e c o r d ( t h i s ) ;  
 } ,  
  
 g e t L a s t R e c o r d : f u n c t i o n   ( )   {  
         r e t u r n   D a t a s e t . g e t L a s t R e c o r d ( t h i s ) ;  
 } ,  
  
 m o v e : f u n c t i o n   ( c o u n t )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . m o v e ( d a t a s e t ,   c o u n t ) ;  
 } ,  
  
 m o v e P r e v : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . m o v e ( d a t a s e t ,   - 1 ) ;  
 } ,  
  
 m o v e N e x t : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . m o v e ( d a t a s e t ,   1 ) ;  
 } ,  
  
 m o v e F i r s t : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( ! d a t a s e t . i s P a g e L o a d e d ( 1 ) )   {  
                 D a t a s e t . l o a d P a g e ( d a t a s e t ,   1 ) ;  
         }  
         D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
         D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   t r u e ,   ( ! D a t a s e t . i s R e c o r d V a l i d ( d a t a s e t . r e c o r d ) ) ) ;  
 } ,  
  
 m o v e L a s t : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( ! d a t a s e t . i s P a g e L o a d e d ( d a t a s e t . p a g e C o u n t ) )   {  
                 D a t a s e t . l o a d P a g e ( d a t a s e t ,   d a t a s e t . p a g e C o u n t ) ;  
         }  
         D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   d a t a s e t . g e t L a s t R e c o r d ( ) ) ;  
         D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   ( ! D a t a s e t . i s R e c o r d V a l i d ( d a t a s e t . r e c o r d ) ) ,   t r u e ) ;  
 } ,  
  
 f i n d : f u n c t i o n   ( f i e l d N a m e s ,   v a l u e s ,   s t a r t R e c o r d )   {  
         f u n c t i o n   i s M a t c h i n g ( f i e l d N a m e s ,   v a l u e s ,   r e c o r d )   {  
                 v a r   r e s u l t   =   t r u e ;  
                 f o r   ( v a r   j   =   0 ;   j   <   f i e l d N a m e s . l e n g t h   & &   j   <   v a l u e s . l e n g t h ;   j + + )   {  
                         i f   ( r e c o r d . g e t S t r i n g ( f i e l d N a m e s [ j ] ) ! = v a l u e s [ j ] )   {  
                                 r e s u l t   =   f a l s e ;  
                                 b r e a k ;  
                         }  
                 }  
                 r e t u r n   r e s u l t ;  
         }  
         i f   ( ! f i e l d N a m e s   | |   ! v a l u e s )   {  
                 r e t u r n   f a l s e ;  
         }  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( ! d a t a s e t . r e c o r d )   {  
                 r e t u r n ;  
         }  
         i f   ( i s M a t c h i n g ( f i e l d N a m e s ,   v a l u e s ,   d a t a s e t . r e c o r d ) )   {  
                 r e t u r n   d a t a s e t . r e c o r d ;  
         }  
         v a r   r e c o r d   =   ( s t a r t R e c o r d )   ?   s t a r t R e c o r d   :   d a t a s e t . g e t F i r s t R e c o r d ( ) ;  
         w h i l e   ( r e c o r d )   {  
                 i f   ( i s M a t c h i n g ( f i e l d N a m e s ,   v a l u e s ,   r e c o r d ) )   {  
                         r e t u r n   r e c o r d ;  
                 }  
                 r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
         }  
 } ,  
  
 l o c a t e : f u n c t i o n   ( f i e l d N a m e ,   v a l u e ,   s t a r t R e c o r d )   {  
         f u n c t i o n   i s M a t c h i n g ( f i e l d N a m e ,   v a l u e ,   r e c o r d )   {  
                 v a r   t m p V a l u e   =   r e c o r d . g e t S t r i n g ( f i e l d N a m e ) ;  
                 r e t u r n   ( t m p V a l u e   & &   S y s t e m . c o m p a r e T e x t ( t m p V a l u e . s u b s t r ( 0 ,   l e n ) ,   v a l u e ) ) ;  
         }  
         i f   ( ! v a l u e )   {  
                 r e t u r n   f a l s e ;  
         }  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( ! d a t a s e t . r e c o r d )   {  
                 r e t u r n ;  
         }  
  
         / /T宊SRM嫲_U徾圠k詮  
         i f   ( i s M a t c h i n g ( f i e l d N a m e ,   v a l u e ,   d a t a s e t . r e c o r d ) )   {  
                 r e t u r n   d a t a s e t . r e c o r d ;  
         }  
         v a r   l e n   =   v a l u e . l e n g t h ;  
         v a r   r e c o r d   =   ( s t a r t R e c o r d )   ?   s t a r t R e c o r d   :   d a t a s e t . g e t F i r s t R e c o r d ( ) ;  
         / /_猻痥詮  
         w h i l e   ( r e c o r d )   {  
                 i f   ( i s M a t c h i n g ( f i e l d N a m e ,   v a l u e ,   r e c o r d ) )   {  
                         r e t u r n   r e c o r d ;  
                 }  
                 r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
         }  
 } ,  
  
 / *  
 p o s t R e c o r d : f u n c t i o n   ( )   {  
         D a t a s e t . p o s t R e c o r d ( t h i s ) ;  
         r e t u r n   t r u e ;  
 } ,  
 * /  
  
 v a l i d D a t a s e t   :   f u n c t i o n ( ) {  
 	 r e t u r n   D a t a s e t . v a l i d A l l R e c o r d ( t h i s ) ;  
 } ,  
  
 i n s e r t R e c o r d W i t h V a l i d a t e : f u n c t i o n   ( m o d e )   {  
         D a t a s e t . i n s e r t R e c o r d ( t h i s ,   m o d e ,   t r u e ) ;  
 } ,  
  
 i n s e r t R e c o r d : f u n c t i o n   ( m o d e )   {  
         D a t a s e t . i n s e r t R e c o r d ( t h i s ,   m o d e ) ;  
 } ,  
  
 d e l e t e R e c o r d : f u n c t i o n   ( )   {  
         D a t a s e t . d e l e t e R e c o r d ( t h i s ) ;  
 } ,  
  
 c o p y R e c o r d : f u n c t i o n   ( r e c o r d ,   f i e l d M a p )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . c o p y R e c o r d ( d a t a s e t ,   r e c o r d ,   f i e l d M a p ) ;  
 } ,  
  
 l o a d P a g e : f u n c t i o n   ( p a g e I n d e x )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . l o a d P a g e ( d a t a s e t ,   p a g e I n n d e x ) ;  
 } ,  
  
 l o a d D e t a i l : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . l o a d D e t a i l ( d a t a s e t ) ;  
 } ,  
  
 i s P a g e L o a d e d : f u n c t i o n   ( p a g e I n d e x )   {  
         v a r   d a t a s e t   =   t h i s ;  
         r e t u r n   d a t a s e t . l o a d e d P a g e [ p a g e I n d e x   -   1 ] ;  
 } ,  
  
 m o v e T o P a g e : f u n c t i o n   ( p a g e I n d e x )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f   ( ! d a t a s e t . i s P a g e L o a d e d ( p a g e I n d e x ) )   {  
                 D a t a s e t . l o a d P a g e ( d a t a s e t ,   p a g e I n d e x ) ;  
         }  
         v a r   r e c o r d   =   d a t a s e t . g e t F i r s t R e c o r d ( ) ;  
         w h i l e   ( r e c o r d )   {  
                 i f   ( r e c o r d . p a g e I n d e x   > =   p a g e I n d e x )   {  
                         D a t a s e t . s e t R e c o r d ( d a t a s e t ,   r e c o r d ) ;  
                         b r e a k ;  
                 }  
                 r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
         }  
 } ,  
  
 s e t M a s t e r D a t a s e t : f u n c t i o n   ( m a s t e r D a t a s e t ,   m a s t e r K e y F i e l d s ,   d e t a i l K e y F i e l d s )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . s e t M a s t e r D a t a s e t ( d a t a s e t ,   m a s t e r D a t a s e t ,   m a s t e r K e y F i e l d s ,   d e t a i l K e y F i e l d s ) ;  
 } ,  
 r e l o a d D a t a : f u n c t i o n   ( c a l l b a c k )   {  
         v a r   d a t a s e t   =   t h i s ;  
         i f ( S y s t e m . i s T r u e ( d a t a s e t . p a g i n a t e ) ) {  
             d a t a s e t . f e t c h D a t a ( n u l l ,   c a l l b a c k ) ;  
         }  
         e l s e {  
             D a t a s e t . r e l o a d D a t a ( d a t a s e t ,   c a l l b a c k ) ;  
         }  
 } ,  
 f l u s h D a t a : f u n c t i o n   ( p a g e I n d e x )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . f l u s h D a t a ( d a t a s e t ,   p a g e I n d e x ) ;  
 } ,  
 p a g i n a t e F l u s h D a t a : f u n c t i o n   ( p a g e I n d e x )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . p a g i n a t e F l u s h D a t a ( d a t a s e t ,   p a g e I n d e x ) ;  
 } ,  
 f e t c h D a t a : f u n c t i o n   ( p a g e I n d e x ,   c a l l b a c k )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . f e t c h D a t a ( d a t a s e t ,   p a g e I n d e x ,   c a l l b a c k ) ;  
 } ,  
 s e l e c t A l l D a t a : f u n c t i o n ( s e l e c t ) {  
 	     v a r   d a t a s e t   =   t h i s ;  
 	     / / d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	     v a r   r e c o r d   =   d a t a s e t . g e t F i r s t R e c o r d ( ) ;  
 	     w h i l e ( r e c o r d ) {  
 	         r e c o r d . s e t V a l u e ( " s e l e c t " ,   S y s t e m . i s T r u e ( s e l e c t ) ) ;  
 	         r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
 	     }  
 	     / / d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 } ,  
  
 c l e a r D a t a : f u n c t i o n   ( )   {  
         v a r   d a t a s e t   =   t h i s ;  
         D a t a s e t . c l e a r D a t a ( d a t a s e t ) ;  
 } ,  
  
 p a r a m e t e r s : f u n c t i o n   ( )   {  
         r e t u r n   t h i s . _ p a r a m e t e r s ;  
 } ,  
  
 c l e a r P a r a m e t e r s : f u n c t i o n ( ) {  
         t h i s . _ p a r a m e t e r s   =   n e w   P a r a m e t e r S e t ( ) ;  
 } ,  
 a d d F i e l d : f u n c t i o n   ( o p t )   {  
         v a r   d a t a s e t   =   t h i s ;  
  
         d a t a s e t . _ q u e r y F i e l d s [ d a t a s e t . _ q u e r y F i e l d s . l e n g t h ]   =   o p t . f n ;  
  
         v a r   f i e l d   =   n e w   F i e l d ( ) ;  
         v a r   i   =   d a t a s e t . f i e l d s . l e n g t h ;  
         d a t a s e t . f i e l d s [ " _ i n d e x _ "   +   o p t . f n ]   =   i ;  
         d a t a s e t . f i e l d s [ i ]   =   f i e l d ;  
         d a t a s e t . f i e l d s . f i e l d C o u n t + + ;  
         f i e l d . i n d e x   =   i ;  
         f i e l d . d a t a s e t   =   d a t a s e t ;  
         f i e l d . f i e l d s   =   d a t a s e t . f i e l d s ;  
         f i e l d . n a m e   =   o p t . f n ;  
         f i e l d . l a b e l   =   o p t . l   | |   o p t . f n ;  
         f i e l d . f i e l d N a m e   =   o p t . f n ;  
         f i e l d . d a t a T y p e   =   o p t . d t ;  
         f i e l d . t o o l T i p   =   " " ;  
  
         f i e l d . e x t r a   =   " f i e l d " ;  
  
         f i e l d . f o r m a t =   o p t . f   | |   " " ;  
      
         i f ( t y p e o f ( o p t . v )   = =   ' u n d e f i n e d '   )  
         	 f i e l d . v i s i b l e =     t r u e ;  
         e l s e  
                 f i e l d . v i s i b l e =     o p t . v ; 	  
                  
         f i e l d . r e a d O n l y =   o p t . r   | |   f a l s e ;  
         f i e l d . e d i t a b l e =   o p t . e   | |   f a l s e ;  
         f i e l d . r e q u i r e d =   o p t . r e q   | |   f a l s e ;  
         f i e l d . d r o p D o w n =   o p t . d d   | |   " " ;  
  
         f i e l d . v a l i d T y p e =     o p t . v t   | |   " " ;  
         f i e l d . v a l i d M s g =   o p t . v m   | |   " " ;  
  
         f i e l d . k e y F i e l d =   o p t . k f   | |   " " ;  
          
         i f ( o p t . d f )  
         	 f i e l d . d b F i e l d   =   o p t . d f   ;  
         i f ( o p t . d v ) {  
         	  
         	 f i e l d . d e f a u l t V a l u e   =   o p t . d v   ;  
          
         }  
         	  
         i f ( o p t . s )  
         	 f i e l d . s i z e   =   o p t . s   ;  
         	  
         i f ( o p t . c b )  
         	 f i e l d . c h e c k b o x   =   o p t . c b   ;  
         i f ( o p t . r b )  
         	 f i e l d . r a d i o b o x   =   o p t . r b   ;  
         	 	 	 	 	 	  
         	 	 	 	 	 	  
         i f ( o p t . c a )  
         	 f i e l d . c h e c k b o x A t t r C o d e   =   o p t . c a   ;  
         i f ( o p t . r a )  
         	 f i e l d . r a d i o b o x A t t r C o d e   =   o p t . r a   ;  
         	  
         i f ( o p t . d 2 )  
         	 f i e l d . d a t a T y p e 2   =   o p t . d 2   ;  
         	  
         i f ( o p t . c b )  
         	 f i e l d . c h e c k b o x   =   o p t . c b   ;  
         i f ( o p t . r b )  
         	 f i e l d . r a d i o b o x   =   o p t . r b   ;  
         	 	 	 	 	 	  
         f i e l d . s u b L i s t = " " ;  
         f i e l d . i s R e s u l t =   o p t . i r   | |   " " ;  
         f i e l d . r e s u l t C o d e = " " ;  
  
         s w i t c h   ( o p t . d t )   {  
             c a s e   " i n t " :  
                 f i e l d . e d i t o r T y p e   =   " t e x t " ;  
                 f i e l d . a l i g n   =   " r i g h t " ;  
                 f i e l d . v A l i g n   =   " t o p " ;  
                 b r e a k ;  
             d e f a u l t :  
                 f i e l d . e d i t o r T y p e   =   " t e x t " ;  
                 f i e l d . a l i g n   =   " l e f t " ;  
                 f i e l d . v A l i g n   =   " t o p " ;  
                 b r e a k ;  
         }  
         r e t u r n   f i e l d ;  
  
 }  
 } ;  
 / * *  
 a d d F i e l d : f u n c t i o n   ( f i e l d N a m e ,   d a t a T y p e )   {  
         v a r   d a t a s e t   =   t h i s ;  
  
         d a t a s e t . _ q u e r y F i e l d s [ d a t a s e t . _ q u e r y F i e l d s . l e n g t h ]   =   f i e l d N a m e ;  
  
         v a r   f i e l d   =   n e w   F i e l d ( ) ;  
         v a r   i   =   d a t a s e t . f i e l d s . l e n g t h ;  
         d a t a s e t . f i e l d s [ " _ i n d e x _ "   +   f i e l d N a m e ]   =   i ;  
         d a t a s e t . f i e l d s [ i ]   =   f i e l d ;  
         d a t a s e t . f i e l d s . f i e l d C o u n t + + ;  
         f i e l d . i n d e x   =   i ;  
         f i e l d . d a t a s e t   =   d a t a s e t ;  
         f i e l d . f i e l d s   =   d a t a s e t . f i e l d s ;  
         f i e l d . n a m e   =   f i e l d N a m e ;  
         f i e l d . l a b e l   =   f i e l d N a m e ;  
         f i e l d . f i e l d N a m e   =   f i e l d N a m e ;  
         f i e l d . d a t a T y p e   =   d a t a T y p e ;  
         f i e l d . t o o l T i p   =   " " ;  
  
         f i e l d . e x t r a   =   " f i e l d " ;  
  
         f i e l d . f o r m a t = " " ;  
         f i e l d . v i s i b l e = t r u e ;  
         f i e l d . r e a d O n l y = f a l s e ;  
         f i e l d . e d i t a b l e = f a l s e ;  
         f i e l d . r e q u i r e d = f a l s e ;  
         f i e l d . d r o p D o w n = " " ;  
  
         f i e l d . v a l i d T y p e = " " ;  
         f i e l d . v a l i d M s g = " " ;  
  
         f i e l d . k e y F i e l d = " " ;  
         f i e l d . s u b L i s t = " " ;  
         f i e l d . i s R e s u l t = " " ;  
         f i e l d . r e s u l t C o d e = " " ;  
  
         s w i t c h   ( d a t a T y p e )   {  
             c a s e   " i n t " :  
                 f i e l d . e d i t o r T y p e   =   " t e x t " ;  
                 f i e l d . a l i g n   =   " r i g h t " ;  
                 f i e l d . v A l i g n   =   " t o p " ;  
                 b r e a k ;  
             d e f a u l t :  
                 f i e l d . e d i t o r T y p e   =   " t e x t " ;  
                 f i e l d . a l i g n   =   " l e f t " ;  
                 f i e l d . v A l i g n   =   " t o p " ;  
                 b r e a k ;  
         }  
         r e t u r n   f i e l d ;  
  
 }  
 } ;  
 * /  
  
 / *     D a t a s e t U t i l   * /  
  
 D a t a s e t . m a x A u t o G e n I D   =   0 ;  
  
 D a t a s e t . g e t A u t o G e n I D   =   f u n c t i o n ( ) {  
 	 D a t a s e t . m a x A u t o G e n I D + + ;  
 	 r e t u r n   " _ _ " + D a t a s e t . m a x A u t o G e n I D ;  
 }  
  
 D a t a s e t . d o w n l o a d D a t a 2   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ,   l o a d C a l l b a c k ) {  
  
 	 i f   ( t y p e o f ( p a g e I n d e x ) = = " u n d e f i n e d " )   {  
 	 	 p a g e I n d e x = d a t a s e t . p a g e I n d e x ;  
 	 }  
         d a t a s e t . c l e a r D a t a ( ) ;  
  
         i f (   d a t a s e t . p a g i n a t e   ) {  
 	 	 / /兎S謃SRMv剺uep  
 	 	 i f   ( ! p a g e I n d e x )  
 	 	 	 p a g e I n d e x   =   " 1 " ;  
  
 	 	 / /兎S謐螻 榰v刌'\  
 	         v a r   p a g e S i z e   =   d a t a s e t . p a g e S i z e ;  
 	         i f   ( ! p a g e S i z e )  
 	 	 	 p a g e S i z e   =   0 ;  
 	 	 / /\榰epO\N:S耬pO ?~賕Rhz  
 	 	 / / a l e r t ( " p a g e I n d e x   n u m b e r " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e I n d e x " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e S i z e " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e I n d e x " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e I n d e x " ,   p a g e I n d e x ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e S i z e " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e S i z e " , p a g e S i z e ) ;  
 	 }  
  
 	 v a r   c a l l b a c k   =   f u n c t i o n ( x m l ) {  
 	 	 v a r   d a t a D o c   =   n e w   A c t i v e X O b j e c t ( " M i c r o s o f t . X M L D O M " ) ;  
 	 	 d a t a D o c . a s y n c = f a l s e ;  
 	         d a t a D o c . l o a d X M L (   x m l   ) )   ;  
 	         v a r   r o o t   =   d a t a D o c . d o c u m e n t E l e m e n t ;  
 	         / /  
 	         i f ( ! r o o t )  
 	         	 r e t u r n   ;  
 	 	 v a r   t o t a l C o u n t   =   r o o t . g e t A t t r i b u t e ( " t o t a l C o u n t " ) ;  
 	 	 v a r   p a g e I n d e x   =   r o o t . g e t A t t r i b u t e ( " p a g e I n d e x " ) ;  
 	 	 v a r   p a g e C o u n t   =   r o o t . g e t A t t r i b u t e ( " p a g e C o u n t " ) ;  
 	 	 d a t a s e t . r e c o r d C o u n t   =   t o t a l C o u n t   ;  
 	 	 / /f鬳 d a t a s e t_SRMv剗"_榰ep  
 	 	 d a t a s e t . p a g e I n d e x = S y s t e m . g e t I n t ( p a g e I n d e x ) ;  
 	 	 / /f鬳 d a t a s e tv刞;榰ep  
 	 	 d a t a s e t . p a g e C o u n t = S y s t e m . g e t I n t ( p a g e C o u n t ) ;  
 	 	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
 	 	 d a t a s e t . c l e a r D a t a ( ) ;  
 	 	 i f   ( d a t a D o c . d o c u m e n t E l e m e n t ) {  
 	 	 	 D a t a s e t . a p p e n d F r o m X m l ( d a t a s e t ,   d a t a D o c . d o c u m e n t E l e m e n t ,   t r u e ) ;  
 	 	 }  
 	 	 d a t a s e t . s e t R e c o r d ( d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 	 d a t a s e t . l o a d D e t a i l ( ) ;  
  
 	 	 i f ( t y p e o f ( l o a d C a l l b a c k ) ! = " u n d e f i n e d "   & &   l o a d C a l l b a c k ! = n u l l ) {  
 	 	     l o a d C a l l b a c k ( ) ;  
 	 	 }  
 	 	 d a t a D o c   =   n u l l ;  
 	 	 r o o t   =   n u l l ;  
 	 }  
  
 	 v a r   c m d   =   n e w   D o w n L o a d D a t a C o m m a n d ( ) ;  
 	 c m d . s e t D a t a s e t (   d a t a s e t   )   ;  
 	 c m d . e x e c u t e ( c a l l b a c k ) ;  
  
 }  
  
 D a t a s e t . d o w n l o a d D a t a   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ) {  
  
         i f (   d a t a s e t . p a g i n a t e   ) {  
 	 	 / /兎S謃SRMv剺uep  
 	 	 i f   ( ! p a g e I n d e x )  
 	 	 	 	 p a g e I n d e x   =   " 1 " ;  
  
 	 	 / /兎S謐螻 榰v刌'\  
 	         v a r   p a g e S i z e   =   d a t a s e t . p a g e S i z e ;  
 	         i f   ( ! p a g e S i z e )  
 	 	 	 p a g e S i z e   =   0 ;  
 	 	 / /\榰epO\N:S耬pO ?~賕Rhz  
 	 	 / / a l e r t ( " p a g e I n d e x   n u m b e r " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e I n d e x " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e S i z e " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e I n d e x " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e I n d e x " ,   p a g e I n d e x ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e S i z e " ,   " i n t " ) ;  
 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e S i z e " , p a g e S i z e ) ;  
 	 }  
 	 v a r   c m d   =   n e w   D o w n L o a d D a t a C o m m a n d ( ) ;  
 	 c m d . s e t D a t a s e t (   d a t a s e t   )   ;  
 	 v a r   x m l   =   c m d . e x e c u t e ( ) ;  
 	 v a r   d a t a D o c   =   n e w   A c t i v e X O b j e c t ( " M i c r o s o f t . X M L D O M " ) ;  
 	 d a t a D o c . a s y n c = f a l s e ;  
         d a t a D o c . l o a d X M L (   x m l   )   ;  
         v a r   r o o t   =   d a t a D o c . d o c u m e n t E l e m e n t ;  
         d a t a D o c   =   n u l l ;  
 	 v a r   t o t a l C o u n t   =   r o o t . g e t A t t r i b u t e ( " t o t a l C o u n t " ) ;  
 	 v a r   p a g e I n d e x   =   r o o t . g e t A t t r i b u t e ( " p a g e I n d e x " ) ;  
 	 v a r   p a g e C o u n t   =   r o o t . g e t A t t r i b u t e ( " p a g e C o u n t " ) ;  
 	 d a t a s e t . r e c o r d C o u n t   =   t o t a l C o u n t   ;  
 	 / /f鬳 d a t a s e t_SRMv剗"_榰ep  
 	 d a t a s e t . p a g e I n d e x = S y s t e m . g e t I n t ( p a g e I n d e x ) ;  
 	 / /f鬳 d a t a s e tv刞tv刞;榰ep  
 	 d a t a s e t . p a g e C o u n t = S y s t e m . g e t I n t ( p a g e C o u n t ) ;  
 	 r e t u r n   r o o t ;  
 }  
  
 D a t a s e t . g e t D a t a s e t B y I D   =   f u n c t i o n ( I D ) {  
         r e t u r n   e v a l ( I D ) ;  
         / *  
 	 f o r ( v a r   i = 0 ;   i < D o c u m e n t . a r r a y _ d a t a s e t . l e n g t h ;   i + + ) {  
 	 	 i f   ( D o c u m e n t . a r r a y _ d a t a s e t [ i ] . i d = = I D )   r e t u r n   D o c u m e n t . a r r a y _ d a t a s e t [ i ] ;  
 	 }  
  
 	 v a r   r e s u l t = n u l l ;  
 	 e v a l ( " i f   ( t y p e o f ( " + I D + " ) ! = \ " u n d e f i n e d \ " )   r e s u l t = " + I D + " ; " ) ;  
 	 r e t u r n   r e s u l t ;  
 	 * /  
 }  
  
 D a t a s e t . s e t E l e m e n t D a t a s e t   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ) {  
 	 v a r   _ d a t a s e t ;  
 	 i f   ( t y p e o f ( d a t a s e t ) = = " s t r i n g " ) {  
 	 	 _ d a t a s e t = D a t a s e t . g e t D a t a s e t B y I D ( d a t a s e t ) ;  
 	 }  
 	 e l s e {  
 	 	 _ d a t a s e t = d a t a s e t ;  
 	 }  
 	 v a r   o l d _ d a t a s e t = e l e m e n t . g e t A t t r i b u t e ( " d a t a s e t " ) ;  
  
 	 i f   ( o l d _ d a t a s e t ) {  
 	 	 v a r   a r r a y = o l d _ d a t a s e t . e d i t o r s ;  
 	 	 i f   ( a r r a y )   {  
 	 	     a r r a y . d e l e t e B y D a t a ( e l e m e n t ) ;  
 	 	     i f ( a r r a y . l e n g t h = = 0 )  
 	 	         o l d _ d a t a s e t . e d i t o r s   =   n u l l ;  
 	 	 }  
 	 }  
  
 	 i f   ( _ d a t a s e t ) {  
 	 	 v a r   a r r a y = _ d a t a s e t . e d i t o r s ;  
 	 	 i f   ( ! a r r a y ) {  
 	 	 	 a r r a y = n e w   p A r r a y ( ) ;  
 	 	 	 _ d a t a s e t . e d i t o r s = a r r a y ;  
 	 	 }  
 	 	 i f   ( a r r a y ) {  
             a r r a y . i n s e r t W i t h D a t a ( e l e m e n t ) ;  
 	     }  
 	 }  
 	 e l e m e n t . d a t a s e t = _ d a t a s e t ;  
 }  
  
 D a t a s e t . g e t F i e l d   =   f u n c t i o n ( f i e l d s ,   n a m e ) {  
 	 v a r   f i e l d = n u l l ;  
 	 i f   ( t y p e o f ( n a m e ) = = " n u m b e r " ) {  
 	 	 f i e l d = f i e l d s [ n a m e ] ;  
 	 }  
 	 e l s e   i f   ( t y p e o f ( n a m e ) = = " s t r i n g " ) {  
 	 	 v a r   f i e l d I n d e x = f i e l d s [ " _ i n d e x _ " + n a m e ] ;  
 	 	 i f   ( ! i s N a N ( f i e l d I n d e x ) )   f i e l d = f i e l d s [ f i e l d I n d e x ] ;  
 	 }  
 	 r e t u r n   f i e l d ;  
 }  
  
 D a t a s e t . a p p e n d F r o m X m l   =   f u n c t i o n ( d a t a s e t ,   r o o t ,   i n i t ) {  
 	 i f   ( ! r o o t )   r e t u r n ;  
  
 	 v a r   c u r r e n t ;  
 	 i f   ( r o o t )   {  
 	 	 v a r   r e c o r d N o d e s = r o o t . c h i l d N o d e s ;  
 	 	 f o r   ( v a r   i = 0 ;   i < r e c o r d N o d e s . l e n g t h ;   i + + )   {  
 	 	 	 v a r   r e c o r d N o d e = r e c o r d N o d e s . i t e m ( i ) ;  
 	 	 	 v a r   n e w D a t a = r e c o r d N o d e . s e l e c t S i n g l e N o d e ( " n e w " ) ;  
 	 	 	 v a r   r e c o r d = n e w D a t a . t e x t . s p l i t ( " , " ) ;  
 	 	 	 r e c o r d . r e c o r d S t a t e = " n o n e " ;  
 	 	 	 d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   r e c o r d ) ;  
 	 	 	 i f   ( i n i t )   i n i t R e c o r d ( r e c o r d ,   d a t a s e t ) ;  
 	 	 }  
 	 }  
 	 r e t u r n   c u r r e n t ;  
 }  
  
 D a t a s e t . s e t R e c o r d   =   f u n c t i o n ( d a t a s e t ,   r e c o r d ) {  
 	 D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   r e c o r d ) ;  
 	 i f   ( r e c o r d ) {  
 	 	 D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   f a l s e ,   f a l s e ) ;  
 	 	 D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   f a l s e ,   f a l s e ) ;  
 	 }  
 }  
 D a t a s e t . g e t F i r s t R e c o r d   =   f u n c t i o n ( d a t a s e t ) {  
 	 v a r   r e c o r d = d a t a s e t . f i r s t U n i t ;  
 	 i f   ( r e c o r d   & &   ! D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) )   r e c o r d = r e c o r d . g e t N e x t R e c o r d ( ) ;  
 	 r e t u r n   r e c o r d ;  
 }  
  
 D a t a s e t . g e t L a s t R e c o r d   =   f u n c t i o n ( d a t a s e t ) {  
 	 v a r   r e c o r d = d a t a s e t . l a s t U n i t ;  
 	 i f   ( ! D a t a s e t . i s R e c o r d V a l i d ( r e c o r d )   & &   r e c o r d )   r e c o r d = r e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 r e t u r n   r e c o r d ;  
 }  
  
 / /y鸕▼癬Un8h  
 D a t a s e t . m o v e   =   f u n c t i o n ( d a t a s e t ,   c o u n t ) {  
 	 v a r   _ r e c o r d = d a t a s e t . r e c o r d ; / / d a t a s e tv刜SRM嫲_U  
 	 i f   ( ! _ r e c o r d )  
 	 	 _ r e c o r d = d a t a s e t . g e t F i r s t R e c o r d ( ) ; / /Y俫渓	_SRM嫲_UR兎S謠,N N*嫲_U  
 	 i f   ( ! _ r e c o r d )   r e t u r n ;  
 	 v a r   r e c o r d = _ r e c o r d ;  
  
 	 i f   ( c o u n t > 0 ) { / /_€RMy鸕 c o u n tN*嫲_U  
 	 	 v a r   o l d _ p a g e I n d e x = r e c o r d . p a g e I n d e x ; / /_SRMv剺u梑}"_ep  
 	 	 v a r   _ e o f = f a l s e ;  
 	 	 f o r ( v a r   i = 0 ;   i < c o u n t ;   i + + ) {  
 	 	 	 v a r   p a g e I n d e x = 0 ;  
 	 	 	 _ r e c o r d = r e c o r d . g e t N e x t R e c o r d ( ) ; / /兎S諲N N*嫲_U  
 	 	 	 i f   ( ! _ r e c o r d   | |   ( _ r e c o r d   & &   _ r e c o r d . p a g e I n d e x ! = o l d _ p a g e I n d e x ) ) { / /Y俫淣N N*嫲_UN:zzb€NN N*嫲_UNW(_SRM榰  
 	 	 	 	 i f   ( o l d _ p a g e I n d e x < d a t a s e t . p a g e C o u n t ) {  
 	 	 	 	 	 i f   ( ! d a t a s e t . i s P a g e L o a d e d ( o l d _ p a g e I n d e x + 1 ) ) { / /Y俫渆pcn徹l	埆N弣  
 	 	 	 	 	 	 i f   ( ( i + d a t a s e t . p a g e S i z e < c o u n t )   & &   ( o l d _ p a g e I n d e x + 1 < d a t a s e t . p a g e C o u n t ) ) {  
 	 	 	 	 	 	 	 i + = d a t a s e t . p a g e S i z e - 1 ;  
 	 	 	 	 	 	 	 _ r e c o r d = r e c o r d ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 	 e l s e {  

 	 	 	 	 	 	 	 D a t a s e t . l o a d P a g e ( d a t a s e t ,   o l d _ p a g e I n d e x + 1 ) ;  
 	 	 	 	 	 	 	 _ r e c o r d = r e c o r d . g e t N e x t R e c o r d ( ) ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 	 o l d _ p a g e I n d e x + + ;  
 	 	 	 }  
  
 	 	 	 i f   ( _ r e c o r d ) {  
 	 	 	 	 r e c o r d = _ r e c o r d ;  
 	 	 	 }  
 	 	 	 e l s e {  
 	 	 	 	 _ e o f = t r u e ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
 	 	 D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   ( ! D a t a s e t . i s R e c o r d V a l i d ( d a t a s e t . r e c o r d ) ) ,   _ e o f ) ;  
 	 }  
 	 e l s e { / /_€Ty鸕 c o u n tN*嫲_U  
 	 	 v a r   o l d _ p a g e I n d e x = r e c o r d . p a g e I n d e x  
 	 	 v a r   _ b o f = f a l s e ;  
 	 	 f o r ( v a r   i = c o u n t ;   i < 0 ;   i + + ) {  
 	 	 	 v a r   p a g e I n d e x = 0 ;  
  
 	 	 	 _ r e c o r d = r e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 	 	 i f   ( ! _ r e c o r d   | |   ( _ r e c o r d   & &   _ r e c o r d . p a g e I n d e x ! = o l d _ p a g e I n d e x ) ) {  
 	 	 	 	 i f   ( o l d _ p a g e I n d e x > 1 ) {  
 	 	 	 	 	 i f   ( ! d a t a s e t . i s P a g e L o a d e d ( o l d _ p a g e I n d e x - 1 ) ) {  
 	 	 	 	 	 	 i f   ( ( i + d a t a s e t . p a g e S i z e < 0 )   & &   ( o l d _ p a g e I n d e x > 1 ) ) {  
 	 	 	 	 	 	 	 i + = d a t a s e t . p a g e S i z e - 1 ;  
 	 	 	 	 	 	 	 _ r e c o r d = r e c o r d ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 	 e l s e {  
 	 	 	 	 	 	 	 D a t a s e t . l o a d P a g e ( d a t a s e t ,   o l d _ p a g e I n d e x - 1 ) ;  
 	 	 	 	 	 	 	 _ r e c o r d = r e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 	 o l d _ p a g e I n d e x - - ;  
 	 	 	 }  
  
 	 	 	 i f   ( _ r e c o r d ) {  
 	 	 	 	 r e c o r d = _ r e c o r d ;  
 	 	 	 }  
 	 	 	 e l s e {  
 	 	 	 	 _ b o f = t r u e ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
 	 	 D a t a s e t . s ee t B o f n E o f ( d a t a s e t ,   _ b o f ,   ( ! D a t a s e t . i s R e c o r d V a l i d ( d a t a s e t . r e c o r d ) ) ) ;  
 	 }  
 	 i f   ( r e c o r d )   D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   r e c o r d ) ;  
 }  
  
 / *  
 D a t a s e t . p o s t R e c o r d   =   f u n c t i o n ( d a t a s e t ) {  
         r e t u r n ;  
 	 i f   ( ! d a t a s e t . r e c o r d )   r e t u r n ;  
  
 	 i f   ( ! D a t a s e t . i s R e c o r d V a l i d ( d a t a s e t . r e c o r d ) )   r e t u r n ;  
  
 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y B e f o r e U p d a t e ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
  
 	 i f   ( d a t a s e t . m o d i f i e d ) {  
  
 	 	 v a r   f i e l d C o u n t = d a t a s e t . f i e l d s . f i e l d C o u n t ;  
  
  
 	 	 / /Y俫湅錯pcn柶g	Qs€Tv刐Pepcn柶  
 	 	 v a r   d e t a i l d a t a s e t s = [ ] ;  
 	 	 i f   ( d a t a s e t . d e t a i l D a t a s e t s ) {  
 	 	 	 v a r   u n i t = d a t a s e t . d e t a i l D a t a s e t s . f i r s t U n i t ;  
 	 	 	 w h i l e   ( u n i t   & &   u n i t . d a t a ) { / /_猻痥螻 N*[Pepcn柶  
 	 	 	 	 v a r   d e t a i l _ d a t a s e t = u n i t . d a t a ;  
 	 	 	 	 i f   ( d e t a i l _ d a t a s e t . r e f e r e n c e s . l e n g t h > 0 )   {  
 	 	 	 	 	 v a r   d i s a b l e C o u n t = d e t a i l _ d a t a s e t . d i s a b l e C o n t r o l C o u n t ;  
 	 	 	 	 	 d e t a i l _ d a t a s e t . d i s a b l e C o n t r o l C o u n t = 1 ;  
 	 	 	 	 	 t r y {  
 	 	 	 	 	 	 v a r   c h a n g e d = f a l s e ;  
 	 	 	 	 	 	 D a t a s e t . p o s t R e c o r d ( d e t a i l _ d a t a s e t ) ;  
 	 	 	 	 	 	 d e t a i l _ d a t a s e t . m o v e F i r s t ( ) ;  
 	 	 	 	 	 	 w h i l e   ( ! d e t a i l _ d a t a s e t . _ e o f ) {  
 	 	 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < d e t a i l _ d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 	 	 	 	 v a r   d e t a i l I n d e x = d e t a i l _ d a t a s e t . r e f e r e n c e s [ i ] . d e t a i l I n d e x ;  
 	 	 	 	 	 	 	 	 v a r   m a s t e r I n d e x = d e t a i l _ d a t a s e t . r e f e r e n c e s [ i ] . m a s t e r I n d e x ;  
 	 	 	 	 	 	 	 	 i f   ( d e t a i l _ d a t a s e t . g e t V a l u e ( d e t a i l I n d e x ) ! = d a t a s e t . g e t V a l u e ( m a s t e r I n d e x ) ) {  
 	 	 	 	 	 	 	 	 	 d e t a i l _ d a t a s e t . s e t V a l u e ( d e t a i l I n d e x ,   d a t a s e t . g e t V a l u e ( m a s t e r I n d e x ) ) ;  
 	 	 	 	 	 	 	 	 	 c h a n g e d = t r u e ;  
 	 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 D a t a s e t . p o s t R e c o r d ( d e t a i l _ d a t a s e t ) ;  
 	 	 	 	 	 	 	 d e t a i l _ d a t a s e t . m o v e N e x t ( ) ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 }  
 	 	 	 	 	 f i n a l l y {  
 	 	 	 	 	 	 d e t a i l _ d a t a s e t . d i s a b l e C o n t r o l C o u n t = d i s a b l e C o u n t ;  
 	 	 	 	 	 }  
  
 	 	 	 	 	 i f   ( c h a n g e d ) {  
 	 	 	 	 	 	 d e t a i l d a t a s e t s [ d e t a i l d a t a s e t s . l e n g t h ] = d e t a i l _ d a t a s e t ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 	 }  
 	 	 }  
  
 	 	 s w i t c h   ( d a t a s e t . r e c o r d . r e c o r d S t a t e ) {  
 	 	 	 c a s e   " n o n e " : {  
 	 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e = " m o d i f y " ;  
 	 	 	 	 D a t a s e t . c h a n g e M a s t e r R e c o r d S t a t e ( d a t a s e t ) ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 c a s e   " n e w " : {  
 	 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e = " i n s e r t " ;  
 	 	 	 	 D a t a s e t . c h a n g e M a s t e r R e c o r d S t a t e ( d a t a s e t ) ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 }  
  
 	 	 d a t a s e t . m o d i f i e d = f a l s e ;  
  
 	 	 D a t a s e t . s e t S t a t e ( d a t a s e t ,   " n o n e " ) ;  
  
 	 	 f o r   ( v a r   i = 0 ; i < d e t a i l d a t a s e t s . l e n g t h ; i + + ) {  
 	 	 	 d e t a i l _ d a t a s e t . r e f r e s h C o n t r o l s ( ) ;  
 	 	 	 D a t a s e t . v a l i d a t e C u r s o r ( d e t a i l _ d a t a s e t ) ;  
 	 	 }  
 	 }  
 	 e l s e {  
 	 	 i f   ( d a t a s e t . r e c o r d . r e c o r d S t a t e = = " n e w " ) {  
 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e = " d i s c a r d " ;  
 	 	 	 D a t a s e t . s e t S t a t e ( d a t a s e t ,   " n o n e " ) ;  
 	 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y D e l e t e ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 	 	 D a t a s e t . v a l i d a t e C u r s o r ( d a t a s e t ) ;  
 	 	 }  
 	 }  
 }  
 * /  
  
 D a t a s e t . v a l i d R e c o r d   =   f u n c t i o n ( d a t a s e t ,   t a r g e t R e c o r d ) {  
  
 	 v a r   f i e l d C o u n t = d a t a s e t . f i e l d s . f i e l d C o u n t ;  
 	 v a r   _ r e c o r d   =   n u l l ;  
 	 i f ( t a r g e t R e c o r d )  
 	     _ r e c o r d   =   t a r g e t R e c o r d ;  
 	 e l s e  
 	     _ r e c o r d   =   d a t a s e t . g e t C u r r e n t ( ) ;  
  
 	 i f ( ! _ r e c o r d )   r e t u r n ;  
 	 v a r   E r r o r I t e m   =   [ ] ;  
 	 v a r   E r r o r M e s s a g e   =   [ ] ;  
  
 	 E r r o r M e s s a g e [ 0 ]   =   V a l i d a t o r . E r r o r M e s s a g e ;  
 	 E r r o r I t e m [ 0 ]   =   n u l l ;  
  
 	 f o r   ( v a r   i = 0 ;   i < f i e l d C o u n t ;   i + + ) {  
  
 	 	 v a r   v a l i d O b j   =   d a t a s e t . f i e l d s [ i ] ;  
 	 	 / /Y俫湅錥Wk礜f/S陭鹶?^vN嬪[Wk礷/_艠{NN:zz  
 	 	 i f   ( d a t a s e t . f i e l d s [ i ]   & &   ! S y s t e m . i s T r u e ( d a t a s e t . f i e l d s [ i ] . r e a d O n l y )   ) {  
  
 	 	 	 / /?徢 v a l i d T y p e殞嬃  
 	 	 	 v a r   _ d a t a T y p e   =   v a l i d O b j . v a l i d T y p e ;  
 	 	 	 i f ( t y p e o f ( _ d a t a T y p e )   = =   " o b j e c t "   | |   t y p e o f ( V a l i d a t o r [ _ d a t t a T y p e ] )   = =   " u n d e f i n e d " )       c o n t i n u e ;  
 	 	 	 v a r   v a l i d R e s u l t   =   t r u e ;  
  
 	 	 	 i f ( ( v a l i d O b j . r e q u i r e d   = =   f a l s e )   & &   _ r e c o r d . g e t V a l u e ( v a l i d O b j . n a m e )   = =   " " )   c o n t i n u e ;  
  
 	 	 	 s w i t c h ( _ d a t a T y p e ) {  
 	 	 	 	 / / j sQ齟pv剼寢羦 d a t a T y p e  
  
 	 	 	 	 c a s e   " r e p e a t "   :  
 	 	 	 	 c a s e   " l i m i t "   :  
 	 	 	 	 c a s e   " l i m i t B "   :  
 	 	 	 	 c a s e   " s a f e S t r i n g "   :  
 	 	 	 	 c a s e   " f i l t e r "   :  
 	 	 	 	 	 / /Ou( j sQ齟pv剼寢  
 	 	 	 	 	 v a l i d R e s u l t   =   e v a l ( V a l i d a t o r [ _ d a t a T y p e ] ) ;  
 	 	 	 	 	 b r e a k ;  
  
 	 	 	 	 d e f a u l t   :  
 	 	 	 	 	 / /Ou(kcR坔従_v剼寢  
 	 	 	 	 	 v a l i d R e s u l t   =   V a l i d a t o r [ _ d a t a T y p e ] . t e s t ( _ r e c o r d . g e t V a l u e ( v a l i d O b j . n a m e ) ) ;  
 	 	 	 	 	 b r e a k ;  
 	 	 	 }  
  
 	 	 	 i f ( ! v a l i d R e s u l t )  
 	 	 	 {  
 	 	 	 	 v a r   m s g   =   v a l i d O b j . v a l i d M s g ;  
  
 	 	 	 	 i f (   m s g = = u n d e f i n e d   | |   m s g = = n u l l   | |   m s g   = =   " " ) {  
 	 	 	 	 	 m s g = E r r M s g s [ _ d a t a T y p e ] ;  
 	 	 	 	 }  
 	 	 	 	 E r r o r I t e m [ E r r o r I t e m . l e n g t h ]   =   v a l i d O b j ;  
 	 	 	 	 E r r o r M e s s a g e [ E r r o r M e s s a g e . l e n g t h ]   =   E r r o r M e s s a g e . l e n g t h   +   " : "   +   m s g ;  
 	 	 	 }  
 	 	 }  
 	 }  
  
 	 i f ( E r r o r M e s s a g e . l e n g t h   >   1 ) {  
 	 	 M e s s a g e B o x . S h o w ( n u l l ,   E r r o r M e s s a g e . j o i n ( G l o b a l . l i n e D e l i m i t e r ) ,   n u l l ,   ' O K ' ,   ' W a r n i n g ' ) ;  
 	 	 r e t u r n   f a l s e ;  
 	 }  
  
 	 r e t u r n   t r u e ;  
 }  
  
 / /S籧塏哠陭鸑徾圠h!殞v刌t  
 D a t a s e t . v a l i d N e w R e c o r d   =   f u n c t i o n ( d ad a t a s e t ,   t a r g e t R e c o r d ) {  
  
 	 v a r   f i e l d C o u n t = d a t a s e t . f i e l d s . f i e l d C o u n t ;  
 	 v a r   _ r e c o r d   =   n u l l ;  
 	 i f ( t a r g e t R e c o r d )  
 	     _ r e c o r d   =   t a r g e t R e c o r d ;  
 	 e l s e  
 	     _ r e c o r d   =   d a t a s e t . g e t C u r r e n t ( ) ;  
  
 	 i f ( ! _ r e c o r d )   r e t u r n ;  
 	 v a r   E r r o r I t e m   =   [ ] ;  
 	 v a r   E r r o r M e s s a g e   =   [ ] ;  
  
 	 E r r o r M e s s a g e [ 0 ]   =   V a l i d a t o r . E r r o r M e s s a g e ;  
 	 E r r o r I t e m [ 0 ]   =   n u l l ;  
  
 	 f o r   ( v a r   i = 0 ;   i < f i e l d C o u n t ;   i + + ) {  
  
 	 	 v a r   v a l i d O b j   =   d a t a s e t . f i e l d s [ i ] ;  
 	 	 / /Y俫湅錥Wk礜f/S陭鹶?^vN嬪[Wk礷/_艠{NN:zz  
 	 	 i f   ( d a t a s e t . f i e l d s [ i ] ) {  
  
 	 	 	 / /?徢 v a l i d T y p e殞嬃  
 	 	 	 v a r   _ d a t a T y p e   =   v a l i d O b j . v a l i d T y p e ;  
 	 	 	 i f ( t y p e o f ( _ d a t a T y p e )   = =   " o b j e c t "   | |   t y p e o f ( V a l i d a t o r [ _ d a t a T y p e ] )   = =   " u n d e f i n e d " )       c o n t i n u e ;  
 	 	 	 v a r   v a l i d R e s u l t   =   t r u e ;  
  
 	 	 	 i f ( ( v a l i d O b j . r e q u i r e d   = =   f a l s e )   & &   _ r e c o r d . g e t V a l u e ( v a l i d O b j . n a m e )   = =   " " )   c o n t i n u e ;  
  
 	 	 	 s w i t c h ( _ d a t a T y p e ) {  
 	 	 	 	 / / j sQ齟pv剼寢羦 d a t a T y p e  
  
 	 	 	 	 c a s e   " r e p e a t "   :  
 	 	 	 	 c a s e   " l i m i t "   :  
 	 	 	 	 c a s e   " l i m i t B "   :  
 	 	 	 	 c a s e   " s a f e S t r i n g "   :  
 	 	 	 	 c a s e   " f i l t e r "   :  
 	 	 	 	 	 / /Ou( j sQ齟pv剼寢  
 	 	 	 	 	 v a l i d R e s u l t   =   e v a l ( V a l i d a t o r [ _ d a t a T y p e ] ) ;  
 	 	 	 	 	 b r e a k ;  
  
 	 	 	 	 d e f a u l t   :  
 	 	 	 	 	 / /Ou(kcR坔従_v剼寢  
 	 	 	 	 	 v a l i d R e s u l t   =   V a l i d a t o r [ _ d  _ d a t a T y p e ] . t e s t ( _ r e c o r d . g e t V a l u e ( v a l i d O b j . n a m e ) ) ;  
 	 	 	 	 	 b r e a k ;  
 	 	 	 }  
  
 	 	 	 i f ( ! v a l i d R e s u l t )  
 	 	 	 {  
 	 	 	 	 v a r   m s g   =   v a l i d O b j . v a l i d M s g ;  
  
 	 	 	 	 i f (   m s g = = u n d e f i n e d   | |   m s g = = n u l l   | |   m s g   = =   " " ) {  
 	 	 	 	 	 m s g = E r r M s g s [ _ d a t a T y p e ] ;  
 	 	 	 	 }  
 	 	 	 	 E r r o r I t e m [ E r r o r I t e m . l e n g t h ]   =   v a l i d O b j ;  
 	 	 	 	 E r r o r M e s s a g e [ E r r o r M e s s a g e . l e n g t h ]   =   E r r o r M e s s a g e . l e n g t h   +   " : "   +   m s g ;  
 	 	 	 }  
 	 	 }  
 	 }  
  
 	 i f ( E r r o r M e s s a g e . l e n g t h   >   1 ) {  
 	 	 M e s s a g e B o x . S h o w ( n u l l ,   E r r o r M e s s a g e . j o i n ( G l o b a l . l i n e D e l i m i t e r ) ,   n u l l ,   ' O K ' ,   ' W a r n i n g ' ) ;  
 	 	 r e t u r n   f a l s e ;  
 	 }  
  
 	 r e t u r n   t r u e ;  
 }  
  
 D a t a s e t . v a l i d A l l R e c o r d   =   f u n c t i o n ( d a t a s e t ) {  
  
 	 v a r   r e c o r d   =   d a t a s e t . g e t F i r s t R e c o r d ( ) ;  
  
       	 w h i l e ( r e c o r d ) {  
                   i f ( ! D a t a s e t . v a l i d R e c o r d ( d a t a s e t ,   r e c o r d ) ) {  
                   	 r e t u r n   f a l s e ;  
                   }  
                   r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
       	 }  
       	 r e t u r n   t r u e ;  
 }  
  
 D a t a s e t . i n s e r t R e c o r d   =   f u n c t i o n ( d a t a s e t ,   m o d e ,   w i t h V a l i d a t e ) {  
  
 	 / /?徢 V a l i d a t o r殞嬃 d a t a s e tv凲匸?  
 	 i f ( w i t h V a l i d a t e   & &   d a t a s e t . r e c o r d   & &   ! D a t a s e t . v a l i d R e c o r d ( d a t a s e t ) )   r e t u r n ;  
  
 	 / /R$e璮/T&g	梌S飲鸞Wk  
 	 i f ( ! d a t a s e t . j u d g e F i e l d M a s k C o n t r o l ( ) )   r e t u r nr n ;  
  
 	 / / D a t a s e t . p o s t R e c o r d ( d a t a s e t ) ;  
  
  
 	 v a r   p a g e I n d e x = ( d a t a s e t . r e c o r d ) ? d a t a s e t . r e c o r d . p a g e I n d e x : 1 ;  
  
 	 v a r   n e w R e c o r d = [ ] ;  
 	 / /W( D a t a s e tv剶h~觛凬-v刜SRM嫲_Uc襋eN N*嫲_U  
 	 d a t a s e t . i n s e r t U n i t ( m o d e ,   d a t a s e t . r e c o r d ,   n e w R e c o r d ) ;  
 	 i n i t R e c o r d ( n e w R e c o r d ,   d a t a s e t ) ;  
  
 	 s w i t c h   ( m o d e ) {  
 	 	 c a s e   " b e g i n " : {  
 	 	 	 n e w R e c o r d . p a g e I n d e x = 1 ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " e n d " : {  
 	 	 	 n e w R e c o r d . p a g e I n d e x = d a t a s e t . p a g e C o u n t ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 d e f a u l t : {  
 	 	 	 n e w R e c o r d . p a g e I n d e x = p a g e I n d e x ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
  
 	 n e w R e c o r d . r e c o r d S t a t e = " n e w " ;  
 	 n e w R e c o r d . r e c o r d n o = 9 9 9 9 ;  
  
 	 v a r   _ m a s t e r D a t a s e t = d a t a s e t . m a s t e r D a t a s e t ;  
 	 i f   ( _ m a s t e r D a t a s e t ) {  
 	 	 i f   ( _ m a s t e r D a t a s e t . r e c o r d ) {  
 	 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 v a r   f i e l d I n d e x = d a t a s e t . r e f e r e n c e s [ i ] . m a s t e r I n d e x ;  
 	 	 	 	 i f   ( _ m a s t e r D a t a s e t . g e t S t r i n g ( f i e l d I n d e x ) = = " " ) {  
 	 	 	 	 	 v a r   f i e l d = _ m a s t e r D a t a s e t . g e t F i e l d ( f i e l d I n d e x ) ;  
 	 	 	 	 	 s w i t c h   ( f i e l d . d a t a T y p e )   {  
 	 	 	 	 	 c a s e   " s t r i n g " :  
 	 	 	 	 	 	 _ m a s t e r D a t a s e t . s e t V a l u e ( f i e l d I n d e x ,   D a t a s e t . g e t A u t o G e n I D ( ) ) ;  
 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 c a s e   " i n t " :  
 	 	 	 	 	 	 v a r   m a x n u m = 0 ;  
 	 	 	 	 	 	 v a r   r e c o r d = _ m a s t e r D a t a s e t . f i r s t U n i t ;  
 	 	 	 	 	 	 w h i l e   ( r e c o r d ) {  
 	 	 	 	 	 	 	 i f   ( r e c o r d . g e t V a l u e ( f i e l d I n d e x ) > m a x n u m )   {  
 	 	 	 	 	 	 	 	 m a x n u m = r e c o r d . g e t V a l u e ( f i e l d I n d e x ) ;  
 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 r e c o r d = r e c o r d . n e x t U n i t ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 	 _ m a s t e r D a t a s e t . s e t V a l u e ( f i e l d I n d e x ,   m a x n u m + 1 ) ;  
 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 / / D a t a s e t . p o s t R e c o r d ( _ m a s t e r D a t a s e t ) ;  
  
  
 	 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 v a r   r e f e r e n c e = d a t a s e t . r e f e r e n c e s [ i ] ;  
 	 	 	 	 n e w R e c o r d [ r e f e r e n c e . d e t a i l I n d e x ] =  
 	 	 	 	 	 _ m a s t e r D a t a s e t . g e t V a l u e ( r e f e r e n c e . m a s t e r I n d e x ) ;  
 	 	 	 }  
  
 	 	 }  
 	 	 e l s e {  
 	 	 	 t h r o w   C o n s t . E r r N o M a s t e r R e c o r d ;  
 	 	 }  
 	 }  
  
 	 d a t a s e t . s t a t e = " i n s e r t " ;  
 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y I n s e r t ,   d a t a s e t ,   d a t a s e t . r e c o r d ,   [ m o d e ,   n e w R e c o r d ] ) ;  
 	 D a t a s e t . s e t R e c o r d ( d a t a s e t ,   n e w R e c o r d ) ;  
  
 	 d a t a s e t . m o d i f i e d = f a l s e ;  
 }  
  
 D a t a s e t . d e l e t e R e c o r d   =   f u n c t i o n ( d a t a s e t ) {  
 	 i f   ( ! d a t a s e t . r e c o r d )   r e t u r n ;  
  
 	 / /R$e璮/T&g	梌S飲鸞Wk  
 	 i f ( ! d a t a s e t . j u d g e F i e l d M a s k C o n t r o l ( ) )   r e t u r n ;  
  
 	 E d i t o r . n e e d U p d aa t e E d i t o r = f a l s e ;  
 	 t r y {  
 	 	 i f   ( d a t a s e t . r e c o r d . r e c o r d S t a t e = = " n e w "   | |   d a t a s e t . r e c o r d . r e c o r d S t a t e = = " i n s e r t " ) {  
  
 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e = " d i s c a r d " ;  
 	 	 }  
 	 	 e l s e {  
  
 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e = " d e l e t e " ;  
 	 	 	 D a t a s e t . c h a n g e M a s t e r R e c o r d S t a t e ( d a t a s e t ) ;  
 	 	 }  
  
 	 	 d a t a s e t . m o d i f i e d = f a l s e ;  
  
 	 	 d a t a s e t . s t a t e = " n o n e " ;  
 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y D e l e t e ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 	 D a t a s e t . v a l i d a t e C u r s o r ( d a t a s e t ) ;  
 	 }  
 	 f i n a l l y {  
 	 	 E d i t o r . n e e d U p d a t e E d i t o r = t r u e ;  
 	 }  
 }  
  
 D a t a s e t . c o p y R e c o r d   =   f u n c t i o n ( d a t a s e t ,   r e c o r d ,   f i e l d M a p ) {  
 	 i f   ( f i e l d M a p ) {  
 	 	 v a r   f i e l d m a p s = [ ] ;  
 	 	 v a r   f i e l d s = f i e l d M a p . s p l i t ( " ; " ) ;  
 	 	 v a r   f i e l d 1 = " " ,   f i e l d 2 = " " ;  
 	 	 f o r ( v a r   i = 0 ;   i < f i e l d s . l e n g t h ;   i + + ) {  
 	 	 	 f i e l d m a p s [ i ] = { } ;  
 	 	 	 v a r   i n d e x = f i e l d s [ i ] . i n d e x O f ( " = " ) ;  
 	 	 	 i f   ( i n d e x > = 0 ) {  
 	 	 	 	 f i e l d 1 = f i e l d s [ i ] . s u b s t r ( 0 ,   i n d e x ) ;  
 	 	 	 	 f i e l d 2 = f i e l d s [ i ] . s u b s t r ( i n d e x + 1 ) ;  
 	 	 	 }  
 	 	 	 e l s e {  
 	 	 	 	 f i e l d 1 = f i e l d s [ i ] ;  
 	 	 	 	 f i e l d 2 = f i e l d s [ i ] ;  
 	 	 	 }  
  
 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d 2 ) ;  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   d a t a s e t . s e t V a l u e ( f i e l d 1 ,   v a l u e ) ;  
 	 	 }  
 	 }  
 	 e l s e {  
 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 	 v a r   f i e l d N a m e = d a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 	 v a r   f i e l d = r e c o r d . d a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 	 i f   ( f i e l d )   {  
 	 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   d a t a s e t . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 	 }  
 	 	 }  
 	 }  
 }  
 D a t a s e t . l o a d P a g e   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ) {  
  
 	 i f   ( ! d a t a s e t . a u t o L o a d P a g e   | |   p a g e I n d e x < 1   | |   p a g e I n d e x > d a t a s e t . p a g e C o u n t   | |   d a t a s e t . i s P a g e L o a d e d ( p a g e I n d e x ) )   r e t u r n ;  
 	 i f   ( d a t a s e t . m a s t e r D a t a s e t )   t h r o w   C o n s t . E r r L o a d P a g e O n D e t a i l D a t a s e t ;  
  
 	 v a r   x m l N o d e = D a t a s e t . d o w n l o a d D a t a ( d a t a s e t ,   p a g e I n d e x ) ;  
 	 i f   ( x m l N o d e ) {  
 	 	 v a r   t m p A r r a y = n e w   p A r r a y ( ) ;  
 	 	 t m p A r r a y . f i e l d s = d a t a s e t . f i e l d s ;  
  
         	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	 	 D a t a s e t . a p p e n d F r o m X m l ( t m p A r r a y ,   x m l N o d e ) ;  
  
 	 	 v a r   r e c o r d = t m p A r r a y . l a s t U n i t ;  
 	 	 w h i l e   ( r e c o r d ) {  
 	 	 	 i n i t R e c o r d ( r e c o r d ,   d a t a s e t ) ;  
 	 	 	 r e c o r d . p a g e I n d e x = p a g e I n d e x ;  
 	 	 	 r e c o r d = r e c o r d . p r e v U n i t ;  
 	 	 }  
  
 	 	 v a r   i n s e r t e d = f a l s e ;  
 	 	 v a r   r e c o r d = d a t a s e t . l a s t U n i t ;  
 	 	 w h i l e   ( r e c o r d ) {  
 	 	 	 i f   ( r e c o r d . p a g e I n d e x < p a g e I n d e x ) {  
 	 	 	 	 d a t a s e t . i n s e r t A r r a y ( " a f t e r " ,   r e c o r d ,   t m p A r r a y ) ;  
 	 	 	 	 i n s e r t e d = t r u e ;  
 	 	 	 	 b r e a k ;  
 	 	 	 }  
 	 	 	 r e c o r d = r e c o r d . p r e v U n i t ;  
 	 	 }  
 	 	 i f   ( ! i n s e r t e d )   d a t a s e t . i n s e r t A r r a y ( " b e g i n " ,   n u l l ,   t m p A r r a y ) ;  
 	 	 d e l e t e   t m p A r r a y ;  
  
 	 	 d a t a s e t . l o a d e d P a g e [ p a g e I n d e x - 1 ] = t r u e ;  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 }  
 	 x m l N o d e   =   n u l l ;  
 }  
 D a t a s e t . l o a d D e t a i l   =   f u n c t i o n ( d a t a s e t ) {  
  
 	 i f   ( d a t a s e t . d e t a i l D a t a s e t s ) {  
 	 	 v a r   u n i t = d a t a s e t . d e t a i l D a t a s e t s . f i r s t U n i t ;  
 	 	  
 	 	 w h i l e   ( u n i t   & &   u n i t . d a t a ) {  
 	 	 	 v a r   d e t a i l _ d a t a s e t = u n i t . d a t a ;  
 	 	 	  
 	 	 	 i f   ( d a t a s e t . r e c o r d   & &   d a t a s e t . r e c o r d . r e c o r d S t a t e ! = " i n s e r t "   & &  
 	 	 	 	 d a t a s e t . r e c o r d . r e c o r d S t a t e ! = " n e w " ) {  
  
 	 	 	 	 	 D a t a s e t . v a l i d a t e C u r s o r ( d e t a i l _ d a t a s e t ) ;  
 	 	 	 	 	 / / i f   ( d e t a i l _ d a t a s e t . l o a d A s N e e d e d   & &   d e t a i l _ d a t a s e t . _ b o f   & &   d e t a i l _ d a t a s e t . _ e o f )   {  
 	 	 	 	 	 i f   ( d e t a i l _ d a t a s e t . l o a d A s N e e d e d )   {  
 	 	 	 	 	 	 v a r   k e y c o d e _ f o u n d e d = f a l s e ;  
  
 	 	 	 	 	 	 i f   ( d a t a s e t . r e c o r d )   {  
 	 	 	 	 	 	 	 v a r   k e y c o d e = " " ;  
 	 	 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < d e t a i l _ d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 	 	 	 	 k e y c o d e + = d a t a s e t . r e c o r d [ d e t a i l _ d a t a s e t . r e f e r e n c e s [ i ] . m a s t e r I n d e x ] ;  
 	 	 	 	 	 	 	 }  
  
 	 	 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < d e t a i l _ d a t a s e t . l o a d e d D e t a i l . l e n g t h ;   i + + ) {  
 	 	 	 	 	 	 	 	 i f   ( d e t a i l _ d a t a s e t . l o a d e d D e t a i l [ i ] = = k e y c o d e ) {  
 	 	 	 	 	 	 	 	 	 k e y c o d e _ f o u n d e d = t r u e ;  
 	 	 	 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 }  
  
 	 	 	 	 	 	 i f   ( ! k e y c o d e _ f o u n d e d ) {  
 	 	 	 	 	 	 	 v a r   d a t a s e t _ i n s e r t e d = f a l s e ;  
  
 	 	 	 	 	 	 	 i f   ( d e t a i l _ d a t a s e t . r e f e r e n c e s . l e n g t h > 0 )   {  
 	 	 	 	 	 	 	 	 v a r   p a r a m e t e r s   =   d e t a i l _ d a t a s e t . p a r a m e t e r s ( ) ;  
 	 	 	 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < d e t a i l _ d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 	 	 	 	 	 p a r a m e t e r s . s e t V a l u e ( d e t a i l _ d a t a s e t . r e f e r e n c e s [ i ] . d e t a i l F i e l d ,  
 	 	 	 	 	 	 	 	 	 	 d a t a s e t . g e t V a l u e ( d e t a i l _ d a t a s e t . r e f e r e n c e s [ i ] . m a s t e r I n d e x ) ) ;  
 	 	 	 	 	 	 	 	 }  
  
 	 	 	 	 	 	 	 	 v a r   x m l N o d e = D a t a s e t . d o w n l o a d D a t a ( d e t a i l _ d a t a s e t ) ;  
 	 	 	 	 	 	 	 	 i f   ( x m l N o d e ) {  
 	 	 	 	 	 	 	 	 	 D a t a s e t . a p p e n d F r o m X m l ( d e t a i l _ d a t a s e t ,   x m l N o d e ,   t r u e ) ;  
 	 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 	 d e l e t e   r e s u l t ;  
 	 	 	 	 	 	 	 }  
  
 	 	 	 	 	 	 	 d e t a i l _ d a t a s e t . l o a d e d D e t a i l [ d e t a i l _ d a t a s e t . l o a d e d D e t a i l . l e n g t h ] = k e y c o d e ;  
 	 	 	 	 	 	 }  
 	 	 	 	 	 }  
  
 	 	 	 }  
  
 	 	 	 d e t a i l _ d a t a s e t . r e f r e s h C o n t r o l s ( ) ;  
 	 	 	 d e t a i l _ d a t a s e t . m o v e F i r s t ( ) ;  
 	 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . s e t M a s t e r D a t a s e t   =   f u n c t i o n ( d a t a s e t ,   m a s t e r D a t a s e t ,   m a s t e r K e y F i e l d s ,   d e t a i l K e y F i e l d s ) {  
 	 i f   ( d a t a s e t . m a s t e r D a t a s e t ) {  
 	 	 v a r   a r r a y = d a t a s e t . m a s t e r D a t a s e t . d e t a i l D a t a s e t s ;  
 	 	 i f   ( a r r a y )   a r r a y . d e l e t e B y D a t a ( d a t a s e t ) ;  
 	 }  
  
 	 i f   ( t y p e o f ( m a s t e r D a t a s e t ) = = " s t r i n g " )   m a s t e r D a t a s e t = D a t a s e t . g e t D a t a s e t B y I D ( m a s t e r D a t a s e t ) ;  
 	 d a t a s e t . m a s t e r D a t a s e t = m a s t e r D a t a s e t ;  
 	 i f   ( m a s t e r D a t a s e t ) {  
 	 	 v a r   a r r a y = m a s t e r D a t a s e t . d e t a i l D a t a s e t s ;  
 	 	 i f   ( ! a r r a y ) {  
 	 	 	 a r r a y = n e w   p A r r a y ( ) ;  
 	 	 	 m a s t e r D a t a s e t . d e t a i l D a t a s e t s = a r r a y ;  
 	 	 }  
 	 	 a r r a y . i n s e r t W i t h D a t a ( d a t a s e t ) ;  
  
 	 	 d a t a s e t . r e f e r e n c e s = [ ] ;  
 	 	 v a r   f i e l d s = m a s t e r K e y F i e l d s . s p l i t ( " , " ) ;  
 	 	 f o r ( v a r   i = 0 ;   i < f i e l d s . l e n g t h ;   i + + ) {  
 	 	 	 v a r   f i e l d = m a s t e r D a t a s e t . g e t F i e l d ( f i e l d s [ i ] ) ;  
  
 	 	 	 i f   ( f i e l d ) {  
 	 	 	 	 v a r   r e f e r e n c e = { } ;  
 	 	 	 	 d a t a s e t . r e f e r e n c e s [ i ] = r e f e r e n c e ;  
 	 	 	 	 r e f e r e n c e . m a s t e r F i e l d = f i e l d . n a m e ;  
 	 	 	 	 r e f e r e n c e . m a s t e r I n d e x = f i e l d . i n d e x ;  
 	 	 	 }  
 	 	 	 e l s e  
 	 	 	 	 t h r o w   C o n s t . E r r C a n t F i n d M a s t e r F i e l d . r e p l a c e ( " % s " ,   f i e l d s [ i ] ) ;  
 	 	 }  
  
 	 	 v a r   f i e l d s = d e t a i l K e y F i e l d s . s p l i t ( " , " ) ;  
 	 	 f o r ( v a r   i = 0 ;   i < f i e l d s . l e n g t h ;   i + + ) {  
 	 	 	 v a r   f i e l d = d a t a s e t . g e t F i e l d ( f i e l d s [ i ] ) ;  
  
 	 	 	 i f   ( f i e l d ) {  
 	 	 	 	 d a t a s e t . r e f e r e n c e s [ i ] . d e t a i l F i e l d = f i e l d . n a m e ;  
 	 	 	 	 d a t a s e t . r e f e r e n c e s [ i ] . d e t a i l I n d e x = f i e l d . i n d e x ;  
 	 	 	 }  
 	 	 	 e l s e  
 	 	 	 	 t h r o w   C o n s t . E r r C a n t F i n d D e t a i l F i e l d . r e p l a c e ( " % s " ,   f i e l d s [ i ] ) ;  
 	 	 }  
 	 	 d e l e t e   f i e l d s ;  
  
 	 	 m a s t e r D a t a s e t . l o a d D e t a i l ( ) ;  
 	 }  
 }  
  
 D a t a s e t . f e t c h D a t a   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ,   c a l l b a c k ) {  
         D a t a s e t . d o w n l o a d D a t a 2 ( d a t a s e t ,   p a g e I n d e x ,   c a l l b a c k ) ;  
 }  
  
 D a t a s e t . f l u s h D a t a   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ) {  
  
 	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
 	 t r y {  
 	 	 i f   ( t y p e o f ( p a g e I n d e x ) = = " u n d e f i n e d " )   {  
 	 	 	 p a g e I n d e x = d a t a s e t . p a g e I n d e x ;  
 	 	 }  
 	 	 d a t a s e t . c l e a r D a t a ( ) ;  
  
 	 	 v a r   x m l N o d e = D a t a s e t . d o w n l o a d D a t a ( d a t a s e t ,   p a g e I n d e x ) ;  
 	 	 i f   ( x m l N o d e ) {  
 	 	 	 D a t a s e t . a p p e n d F r o m X m l ( d a t a s e t ,   x m l N o d e ,   t r u e ) ;  
 	 	 }  
 	 	 d e l e t e   r e s u l t ;  
 	 }  
 	 f i n a l l y {  
 	 	 d a t a s e t . s e t R e c o r d ( d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 	 d a t a s e t . l o a d D e t a i l ( ) ;  
 	 }  
  
 	 D a t a s e t . f i r e E v e n t ( d a t a s e t ,   " a f t e r L o a d D a t a " ,   [ d a t a s e t ] ) ;  
  
 }  
  
 D a t a s e t . p a g i n a t e F l u s h D a t a   =   f u n c t i o n ( d a t a s e t ,   p a g e I n d e x ) {  
  
 	 i f (   ! d a t a s e t . l o a d D a t a A c t i o n     | |   d a t a s e t . l o a d D a t a A c t i o n   = =   " "   | |  
 	 	 d a t a s e t . l o a d D a t a A c t i o n   = =   " u n d i f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
 	 i f (   ! d a t a s e t . l o a d D a t a A c t i o n M e t h o d     | |   d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " "   | |  
 	 	 d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " u n d i f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
     D a t a s e t . f i r e E v e n t ( d a t a s e t ,   " b e f o r e L o a d D a t a " ,   [ d a t a s e t ] ) ;  
 	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	 i f   ( t y p e o f ( p a g e I n d e x ) = = " u n d e f i n e d " )   {  
 	 	 p a g e I n d e x = d a t a s e t . p a g e I n d e x ;  
 	 }  
 	 d a t a s e t . c l e a r D a t a ( ) ;  
  
 	 t r y {  
  
 	       i f (   d a t a s e t . p a g i n a t e   ) {  
 	 	 	 / /兎S謃SRMv剺uep  
 	 	 	 i f   ( p a g e I n d e x = = n u l l )  
 	 	 	 	 p a g e I n d e x   =   " 1 " ;  
  
 	 	 	 / /兎S謐螻 榰v刌'\  
 	 	         v a r   p a g e S i z e   =   d a t a s e t . p a g e S i z e ;  
 	 	         i f   ( ! p a g e S i z e )  
 	 	 	 	 p a g e S i z e   =   0 ;  
 	 	 	 / /\榰epO\N:S耬pO ?~賕Rhz  
 	 	 	 / / a l e r t ( " p a g e I n d e x   n u m b e r " ) ;  
 	 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e I n d e x " ) ;  
 	 	 	 d a t a s e t . p a r a m e t e r s ( ) . d e l P a r a m e t e r ( " p a g e S i z e " ) ;  
 	 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e I n d e x " ,   " i n t " ) ;  
 	 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e I n d e x " ,   p a g e I n d e x ) ;  
 	 	 	 d a t a s e t . p a r r a m e t e r s ( ) . s e t D a t a T y p e ( " p a g e S i z e " ,   " i n t " ) ;  
 	 	 	 d a t a s e t . p a r a m e t e r s ( ) . s e t V a l u e ( " p a g e S i z e " , p a g e S i z e ) ;  
 	 	 }  
  
 	 	 / /兎S謆@g	v凷耬pS耬p?徢 d a t a s e t~腘鯿蠴泇別筶諎?nS颪鍕?ne鄸PN*  
 	 	 / /S耬pOFf/S耬pv刱!^廮艠{O輯罷実Rh~腘鯿蠴沢R別筶誺凷耬pN 侓0  
 	         v a r   p a r a m e t e r s   =   [ ] ;  
 	         v a r   p s   =   d a t a s e t . _ p a r a m e t e r s ;  
 	         f o r (   v a r   i   =   0 ;   i   <   p s . c o u n t ( ) ;   i   + +   ) {  
 	                 v a r   p a r a m e t e r N a m e   =   p s . i n d e x T o N a m e ( i ) ;  
 	         	 v a r   p a r a m e t e r T y p e   =   p s . g e t D a t a T y p e (   p a r a m e t e r N a m e   ) ;  
 	         	 v a r   p a r a m e t e r V a l u e   =   p s . g e t V a l u e (   p a r a m e t e r N a m e   ) ;  
 	         	 i f (   p a r a m e t e r T y p e   = =   " o b j e c t "   ) {  
 	         	 	 p a r a m e t e r s [ i ]   =   p a r a m e t e r V a l u e   ;  
 	         	 } e l s e {  
 	 	         	 v a r   v a l u e   =   p a r s e V a l u e (   p a r a m e t e r V a l u e ,   p a r a m e t e r T y p e   ) ;  
 	         	 	 p a r a m e t e r s [ i ]   =   v a l u e   ;  
 	         	 }  
 	         }  
  
  
  
 	         / / = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = C a l l   B a c k   F u n c t i o n   S t a r t   H e r e  
 	         v a r   c a l l B a c k   =   f u n c t i o n   ( r e p l y )   {  
 	 	 	 / /兎S謌Rhz飶訴辷 X M Lh<_v刐W{&N2  
 	 	 	 v a r   x m l   =   r e p l y . g e t R e s u l t ( ) ;  
 	 	 	 v a r   d a t a D o c   =   n e w   A c t i v e X O b j e c t ( " M i c r o s o f t . X M L D O M " ) ;  
 	 	 	 d a t a D o c . a s y n c = f a l s e ;  
 	 	         d a t a D o c . l o a d X M L (   x m l   )   ;  
 	 	         v a r   r o o t   =   d a t a D o c . d o c u m e n t E l e m e n t ;  
 	 	         d a t a D o c   =   n u l l ;  
 	 	 	 v a r   t o t a l C o u n t   =   =   r o o t . g e t A t t r i b u t e ( " t o t a l C o u n t " ) ;  
 	 	 	 v a r   p a g e I n d e x   =   r o o t . g e t A t t r i b u t e ( " p a g e I n d e x " ) ;  
 	 	 	 v a r   p a g e C o u n t   =   r o o t . g e t A t t r i b u t e ( " p a g e C o u n t " ) ;  
 	 	 	 d a t a s e t . r e c o r d C o u n t   =   t o t a l C o u n t   ;  
 	 	 	 / /f鬳 d a t a s e t_SRMv剗"_榰ep  
 	 	 	 d a t a s e t . p a g e I n d e x = S y s t e m . g e t I n t ( p a g e I n d e x ) ;  
 	 	 	 / /f鬳 d a t a s e tv刞;榰ep  
 	 	 	 d a t a s e t . p a g e C o u n t = S y s t e m . g e t I n t ( p a g e C o u n t ) ;  
  
 	 	 	 i f   ( r o o t ) {  
 	 	 	 	 D a t a s e t . a p p e n d F r o m X m l ( d a t a s e t ,   r o o t ,   t r u e ) ;  
 	 	 	 }  
  
 	 	 	 d a t a s e t . s e t R e c o r d ( d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 	 	 d a t a s e t . l o a d D e t a i l ( ) ;  
 	 	 	 D a t a s e t . f i r e E v e n t ( d a t a s e t ,   " a f t e r L o a d D a t a " ,   [ d a t a s e t ] ) ;  
 	         }  
  
         	 v a r   a j a x C a l l   =   n e w   N D A j a x C a l l ( t r u e ) ;  
         	 a j a x C a l l . r e m o t e C a l l ( d a t a s e t . l o a d D a t a A c t i o n ,   d a t a s e t . l o a d D a t a A c t i o n M e t h o d ,   p a r a m e t e r s ,   c a l l B a c k ,   d a t a s e t . _ q u e r y T y p e , d a t a s e t . _ q u e r y F i e l d s ) ;  
 	 } c a t c h ( e ) {  
 	 	 d a t a s e t . s e t R e c o r d ( d a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 	 d a t a s e t . l o a d D e t a i l ( ) ;  
 	 }  
 }  
  
 D a t a s e t . c l e a r D a t a   =   f u n c t i o n ( d a t a s e t ) {  
 	 d a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
 	 t r y {  
 	 	 i f   ( d a t a s e t . l o a d e d D e t a i l )   d e l e t e   d a t a s e t . l o a d e d D e t a i l ;  
 	 	 i f   ( d a t a s e t . l l o a d e d P a g e )   d e l e t e   d a t a s e t . l o a d e d P a g e ;  
 	 	 d a t a s e t . l o a d e d D e t a i l = [ ] ;  
 	 	 d a t a s e t . l o a d e d P a g e = [ ] ;  
 	 	 i f   ( d a t a s e t . p a g e I n d e x > 0 )   d a t a s e t . l o a d e d P a g e [ d a t a s e t . p a g e I n d e x - 1 ] = t r u e ;  
 	 	 d a t a s e t . c l e a r A l l ( ) ;  
 	 	 d a t a s e t . s e t R e c o r d ( n u l l ) ;  
 	 }  
 	 f i n a l l y {  
 	 	 d a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 	 }  
 }  
  
 D a t a s e t . f i r e E v e n t   =   f u n c t i o n ( d a t a s e t ,   e v e n t N a m e ,   p a r a m ) {  
 	 i f   ( d a t a s e t . d i s a b l e E v e n t C o u n t > 0 )   r e t u r n ;  
 	 v a r   r e s u l t ;  
 	 r e s u l t = D o c u m e n t . f i r e U s e r E v e n t ( D o c u m e n t . g e t E l e m e n t E v e n t N a m e ( d a t a s e t ,   e v e n t N a m e ) ,   p a r a m ) ;  
 	 r e t u r n   r e s u l t ;  
 }  
  
 D a t a s e t . i s R e c o r d V a l i d   =   f u n c t i o n ( r e c o r d ) {  
 	 i f   ( ! r e c o r d )  
 	 	 r e t u r n   f a l s e ;  
 	 e l s e {  
 	 	 v a r   r e s u l t = ( r e c o r d . r e c o r d S t a t e ! = " d e l e t e "   & &   r e c o r d . r e c o r d S t a t e ! = " d i s c a r d "   & &   r e c o r d . v i s i b l e ) ;  
 	 	 v a r   d a t a s e t = r e c o r d . d a t a s e t ;  
 	 	 v a r   m a s t e r D a t a s e t = d a t a s e t . m a s t e r D a t a s e t ;  
 	 	 i f   ( r e s u l t ) {  
 	 	 	 i f   ( m a s t e r D a t a s e t ) {  
 	 	 	 	 i f   ( ! m a s t e r D a t a s e t . r e c o r d )   r e t u r n   f a l s e ;  
  
 	 	 	 	 f o r ( v a r   i = 0 ;   i < d a t a s e t . r e f e r e n c e s . l e n g t h ;   i + + ) {  
 	 	 	 	 	 i f   ( m a s t e r D a t a s e t . g e t V a l u e ( d a t a s e t . r e f e r e n c e s [ i ] . m a s t e r I n d e x ) ! =  
 	 	 	 	 	 	 r e c o r d . g e t V a l u e ( d a t a s e t . r e f e r e n c e s [ i ] . d e t a i l I n d e x ) ) {  
 	 	 	 	 	 	 	 r e s u l t = f a l s e ;  
 	 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
  
 	 	 	 }  
  
  
 	 	 }  
 	 	 r e t u r n   r e s u l t ;  
 	 }  
 }  
  
 D a t a s e t . i s F i e l d E d i t a b l e   =   f u n c t i o n ( d a t a s e t ,   f i e l d ) {  
         v a r   e d i t a b l e = ! ( d a t a s e t . r e a d O n l y   | |   f i e l d . r e a d O n l y ) ;  
         r e t u r n   e d i t a b l e ;  
         / *    O輚Y ,N錞[瀞   * /  
         / *  
 	 i f   ( f i e l d ) {  
 	 	 v a r   e d i t a b l e = ! ( d a t a s e t . r e a d O n l y   | |   f i e l d . r e a d O n l y ) ;  
 	 	 i f   ( d a t a s e t . r e c o r d ) {  
 	 	 	 v a r   r e c o r d S t a t e = d a t a s e t . r e c o r d . r e c o r d S t a t e ;  
 	 	 	 e d i t a b l e = ( e d i t a b l e   & &  
 	 	 	 	 ! ( ( r e c o r d S t a t e = = " n o n e "   | |   r e c o r d S t a t e = = " m o d i f y " )   & &   f i e l d . v a l u e P r o t e c t e d ) ) ;  
 	 	 }  
 	 }  
 	 e l s e {  
 	 	 v a r   e d i t a b l e = t r u e ;  
 	 }  
 	 r e t u r n   e d i t a b l e ;  
 	 * /  
 }  
  
 D a t a s e t . f r e e D a t a s e t   =   f u n c t i o n ( d a t a s e t ) {  
 	 i f   ( d a t a s e t . d e t a i l D a t a s e t s )   d a t a s e t . d e t a i l D a t a s e t s . c l e a r A l l ( ) ;  
 	 i f   ( d a t a s e t . e d i t o r s )   d a t a s e t . e d i t o r s . c l e a r A l l ( ) ;  
 	 d e l e t e   d a t a s e t . r e f e r e n c e s ;  
  
 	 p A r r a y . _ c l e a r A l l ( d a t a s e t . f i e l d s ) ;  
 	 d a t a s e t . c l e a r D a t a ( ) ;  
 	 d e l e t e   d a t a s e t ;  
 }  
  
 D a t a s e t . s e t B o f n E o f   =   f u n c t i o n ( d a t a s e t ,   B o f V a l u e ,   E o f V a l u e ) {  
 	 i f   ( d a t a s e t . _ b o f ! = B o f V a l u e   | |   d a t a s e t . _ e o f ! = E o f V a l u e ) {  
 	 	 d a t a s e t . _ b o f = B o f V a l u e ;  
 	 	 d a t a s e t . _ e o f = E o f V a l u e ;  
 	 	 D a t a s ee t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ,   d a t a s e t . r e c o r d ) ;  
 	 }  
 }  
  
 D a t a s e t . d o _ s e t R e c o r d   =   f u n c t i o n ( d a t a s e t ,   r e c o r d ) {  
 	 i f   ( d a t a s e t . r e c o r d ! = r e c o r d ) {  
 	 	 / *  
 	 	 i f   ( d a t a s e t . r e c o r d ) {  
 	 	 	 D a t a s e t . p o s t R e c o r d ( d a t a s e t ) ;  
 	 	 }  
 	 	 i f   ( d a t a s e t . d e t a i l D a t a s e t s ) {  
 	 	 	 v a r   u n i t = d a t a s e t . d e t a i l D a t a s e t s . f i r s t U n i t ;  
 	 	 	 w h i l e   ( u n i t ) {  
 	 	 	 	 v a r   d e t a i l D a t a s e t = u n i t . d a t a ;  
 	 	 	 	 D a t a s e t . p o s t R e c o r d ( d e t a i l D a t a s e t ) ;  
 	 	 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 	 }  
 	 	 }  
 	 	 * /  
  
  
 	 	 v a r   e v e n t _ r e s u l t = D a t a s e t . f i r e E v e n t ( d a t a s e t ,   " b e f o r e S c r o l l " ,   [ d a t a s e t ] ) ;  
 	 	 i f   ( e v e n t _ r e s u l t ) {  
 	 	 	 t h r o w   e v e n t _ r e s u l t ;  
 	 	 }  
 	 	 d a t a s e t . r e c o r d = r e c o r d ;  
 	 	 d a t a s e t . m o d i f i e d = f a l s e ;  
 	 	 i f   ( d a t a s e t . d i s a b l e C o n t r o l C o u n t < 1 ) {  
 	 	 	 d a t a s e t . l o a d D e t a i l ( ) ;  
 	 	 }  
 	 	 D a t a s e t . f i r e E v e n t ( d a t a s e t ,   " a f t e r S c r o l l " ,   [ d a t a s e t ] ) ;  
 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y S t a t e C h a n g e d ,   d a t a s e t ,   r e c o r d ) ;  
 	 	 D a t a s e t . b r o a d c a s t D a t a s e t M s g ( D a t a s e t . n o t i f y C u r s o r C h a n g e d ,   d a t a s e t ,   r e c o r d ) ;  
 	 }  
  
 }  
  
  
 D a t a s e t . v a l i d a t e C u r s o r   =   f u n c t i o n ( d a t a s e t ) {  
 	 v a r   d o w n _ f o u n d = f a l s e ,   u p _ f o u n d = f a l s e ;  
 	 v a r   c u r R e c o r d = ( d a t a s e t . r e c o r d ) ? d a t a s e t . r e c o r d : d a t a s e t . f i r s t U n i t ;  
  
 	 v a r   r e c o r d = c u r R e c o r d ;  
 	 w h i l e   ( r e c o r d ) {  
 	 	 i f   ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) ) {  
 	 	 	 D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   r e c o r d ) ;  
 	 	 	 u p _ f o u n d = t r u e ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 r e c o r d = r e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 }  
  
 	 v a r   r e c o r d = c u r R e c o r d ;  
 	 w h i l e   ( r e c o r d ) {  
 	 	 i f   ( D a t a s e t . i s R e c o r d V a l i d ( r e c o r d ) ) {  
 	 	 	 D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   r e c o r d ) ;  
 	 	 	 d o w n _ f o u n d = t r u e ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 r e c o r d = r e c o r d . g e t N e x t R e c o r d ( ) ;  
 	 }  
  
 	 i f   ( ! u p _ f o u n d   & &   ! d o w n _ f o u n d )  
 	 	 D a t a s e t . d o _ s e t R e c o r d ( d a t a s e t ,   n u l l ) ;  
  
 	 D a t a s e t . s e t B o f n E o f ( d a t a s e t ,   ( ! u p _ f o u n d ) ,   ( ! d o w n _ f o u n d ) ) ;  
 }  
  
  
 D a t a s e t . b r o a d c a s t D a t a s e t M s g   =   f u n c t i o n ( p r o c ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 i f   ( d a t a s e t . d i s a b l e C o n t r o l C o u n t > 0 )   r e t u r n ;  
 	 v a r   p A r r a y = d a t a s e t . e d i t o r s ;  
 	 i f   ( p A r r a y ) {  
 	 	 v a r   u n i t = p A r r a y . f i r s t U n i t ;  
 	 	 w h i l e   ( u n i t   & &   u n i t . d a t a ) {  
 	 	 	 p r o c ( u n i t . d a t a ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) ;  
 	 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . b r o a d c a s t F i e l d M s g   =   f u n c t i o n ( p r o c ,   d a t a s e t ,   r e c o r d ,   f i e l d ,   r e s e r v e d ) {  
 	 i f   ( d a t a s e t . d i s a b l e C o n t r o l C o u n t > 0 )   r e t u r n ;  
 	 v a r   p A r r a y = d a t a s e t . e d i t o r s ;  
  
 	 i f   ( p A r r a y ) {  
 	 	 v a r   u n i t = p A r r a y . f i r s t U n i t ;  
 	 	 w h i l e   ( u n i t   & &   u n i t . d a t a ) {  
 	 	 	 p r o c ( u n i t . d a t a ,   d a t a s e t ,   r e c o r d ,   f i e l d ,   r e s e r v e d ) ;  
 	 	 	 u n i t = u n i t . n e x t U n i t ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y C u r s o r C h a n g e d   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 i f   ( ! r e c o r d )   b r e a k ;  
  
 	 	 	 v a r   m a x R o w = e l e m e n t . g e t A t t r i b u t e ( " m a x R o w " ) ;  
 	 	 	 i f   ( e l e m e n t . t B o d i e s [ 0 ] . r o w s . l e n g t h > = m a x R o w ) {  
 	 	 	 	 v a r   n e e d R e f r e s h = t r u e ;  
 	 	 	 	 v a r   f i r s t R e c o r d = _ w i n d o w . D a t a t a b l e . g e t T a b l e F i r s t R e c o r d ( e l e m e n t ) ;  
 	 	 	 	 v a r   l a s t R e c o r d = _ w i n d o w . D a t a t a b l e . g e t T a b l e L a s t R e c o r d ( e l e m e n t ) ;  
  
 	 	 	 	 v a r   _ r e c o r d = f i r s t R e c o r d ;  
 	 	 	 	 w h i l e   ( _ r e c o r d ) {  
 	 	 	 	 	 i f   ( _ r e c o r d = = r e c o r d ) {  
 	 	 	 	 	 	 n e e d R e f r e s h = f a l s e ;  
 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
  
 	 	 	 	 	 i f   ( _ r e c o r d = = l a s t R e c o r d )   b r e a k ;  
 	 	 	 	 	 _ r e c o r d = _ r e c o r d . n e x t U n i t ;  
 	 	 	 	 }  
  
 	 	 	 	 i f   ( n e e d R e f r e s h ) {  
 	 	 	 	 	 v a r   c o u n t e r = m a x R o w ;  
 	 	 	 	 	 v a r   t m p R e c o r d = r e c o r d ;  
  
 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < c o u n t e r ;   i + + ) {  
 	 	 	 	 	 	 t m p R e c o r d = t m p R e c o r d . g e t N e x t R e c o r d ( ) ;  
 	 	 	 	 	 	 i f   ( ! t m p R e c o r d )   b r e a k ;  
 	 	 	 	 	 }  
  
 	 	 	 	 	 v a r   s t a r t R e c o r d = r e c o r d ;  
 	 	 	 	 	 t m p R e c o r d = r e c o r d ;  
 	 	 	 	 	 c o u n t e r = m a x R o w - i - 1 ;  
 	 	 	 	 	 f o r ( v a r   i = 0 ;   i < c o u n t e r ;   i + + ) {  
 	 	 	 	 	 	 t m p R e c o r d = t m p R e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 	 	 	 	 	 i f   ( t m p R e c o r d )  
 	 	 	 	 	 	 	 s t a r t R e c o r d = t m p R e c o r d ;  
 	 	 	 	 	 	 e l s e  
 	 	 	 	 	 	 	 b r e a k ;  
 	 	 	 	 	 }  
  
 	 	 	 	 	 _ w i n d o w . D a t a t a b l e . r e f r e s h T a b l e D a t a ( e l e m e n t ,   s t a r t R e c o r d ) ;  
 	 	 	 	 }  
 	 	 	 }  
  
 	 	 	 v a r   r o w = _ w i n d o w . D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( e l e m e n t ,   r e c o r d ) ;  
 	 	 	 i f   ( r o w ) {  
 	 	 	 	 _ w i n d o w . D a t a t a b l e . s e t A c t i v e T a b l e R o w ( r o w ) ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a l a b e l " : {  
 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 e l e m e n t . i s U s e r I n p u t = f a l s e ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a p i l o t " : {  
 	 	 	 _ w i n d o w . D a t a P i l o t . r e f r e s h D a t a P i l o t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " p a g e p i l o t " : {  
 	 	 	 _ w i n d o w . P a g e P i l o t . r e f r e s h P a g e P i l o t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y B e f o r e U p d a t e   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 _ w i n d o w . E d i t o r . u p d a t e E d i t o r I n p u t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y S t a t e C h a n g e d   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 v a r   f i e l d = _ w i n d o w . C o n t r o l . g e t E l e m e n t F i e l d ( e l e m e n t ) ;  
 	 	 	 e l e m e n t . s e t R e a d O n l y ( ! D a t a s e t . i s F i e l d E d i t a b l e ( d a t a s e t ,   f i e l d ) ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a p i l o t " : {  
 	 	 	 _ w i n d o w . D a t a P i l o t . r e f r e s h D a t a P i l o t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 i f   ( e l e m e n t . a c t i v e R o w )   _ w i n d o w . D a t a t a b l e . r e f r e s h T a b l e R o w I n d i c a t e ( e l e m e n t . a c t i v e R o w ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y I n s e r t   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 v a r   r o w ;  
 	 	 	 i f   ( r e c o r d )   r o w = _ w i n d o w . D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( e l e m e n t ,   r e c o r d ) ;  
  
 	 	 	 _ w i n d o w . D a t a t a b l e . i n s e r t T a b l e R e c o r d ( e l e m e n t ,   r e s e r v e d [ 0 ] ,   r o w ,   r e s e r v e d [ 1 ] ) ;  
 	 	 	 i f   ( e l e m e n t . t B o d i e s [ 0 ] . r o w s . l e n g t h > e l e m e n t . g e t A t t r i b u t e ( " m a x R o w " ) ) {  
 	 	 	 	 v a r   l a s t R e c o r d = _ w i n d o w . D a t a t a b l e . g e t T a b l e L a s t R e c o r d ( e l e m e n t ) ;  
 	 	 	 	 i f   ( l a s t R e c o r d ! = r e s e r v e d [ 1 ] ) {  
 	 	 	 	 	 _ w i n d o w . D a t a t a b l e . d e l e t e T a b l e R e c o r d ( e l e m e n t . t B o d i e s [ 0 ] . r o w s [ e l e m e n t . t B o d i e s [ 0 ] . r o w s . l e n g t h - 1 ] ) ;  
 	 	 	 	 }  
 	 	 	 	 e l s e {  
 	 	 	 	 	 _ w i n d o w . D a t a t a b l e . d e l e t e T a b l e R e c o r d ( e l e m e n t . t B o d i e s [ 0 ] . r o w s [ 0 ] ) ;  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y D e l e t e   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 i f   ( r e c o r d ) {  
 	 	 	 	 v a r   r o w = _ w i n d o w . D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( e l e m e n t ,   r e c o r d ) ;  
 	 	 	 	 i f   ( r o w ) {  
 	 	 	 	 	 i f   ( e l e m e n t . t B o d i e s [ 0 ] . r o w s . l e n g t h < = e l e m e n t . g e t A t t r i b u t e ( " m a x R o w " ) ) {  
 	 	 	 	 	 	 v a r   f i r s t R e c o r d = _ w i n d o w . D a t a t a b l e . g e t T a b l e F i r s t R e c o r d ( e l e m e n t ) ;  
 	 	 	 	 	 	 v a r   l a s t R e c o r d = _ w i n d o w . D a t a t a b l e . g e t T a b l e L a s t R e c o r d ( e l e m e n t ) ;  
 	 	 	 	 	 	 i f   ( f i r s t R e c o r d ) {  
 	 	 	 	 	 	 	 v a r   _ r e c o r d = l a s t R e c o r d . g e t N e x t R e c o r d ( ) ;  
 	 	 	 	 	 	 	 i f   ( _ r e c o r d ) {  
 	 	 	 	 	 	 	 	 _ w i n d o w . D a t a t a b l e . i n s e r t T a b l e R e c o r d ( e l e m e n t ,   " e n d " ,   r o w ,   _ r e c o r d ) ;  
 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 	 e l s e {  
 	 	 	 	 	 	 	 	 v a r   _ r e c o r d = f i r s t R e c o r d . g e t P r e v R e c o r d ( ) ;  
 	 	 	 	 	 	 	 	 i f   ( _ r e c o r d )   _ w i n d o w . D a t a t a b l e . i n s e r t T a b l e R e c o r d ( e l e m e n t ,   " b e g i n " ,   r o w ,   _ r e c o r d ) ;  
 	 	 	 	 	 	 	 }  
 	 	 	 	 	 	 }  
 	 	 	 	 	 }  
  
 	 	 	 	 	 _ w i n d o w . D a t a t a b l e . d e l e t e T a b l e R e c o r d ( r o w ) ;  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . n o t i f y R e f r e s h R e c o r d   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 i f   ( r e c o r d ) {  
 	 	 	 	 v a r   r o w = _ w i n d o w . D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( e l e m e n t ,   r e c o r d ) ;  
 	 	 	 	 i f   ( r o w )   _ w i n d o w . D a t a t a b l e . r e f r e s h T a b l e R e c o r d ( r o w ) ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 e l e m e n t . i s U s e r I n p u t = f a l s e ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
  
 	 i f   ( _ w i n d o w . E d i t o r )   _ w i n d o w . E d i t o r . s i z e D o c k E d i t o r ( ) ;  
  
 }  
  
 D a t a s e t . n o t i f y R e f r e s h   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 i f   ( ! S y s t e m . c o m p a r e T e x t ( e l e m e n t . s t y l e . v i s i b i l i t y ,   " h i d d e n " ) )   {  
 	 	 	         i f ( C o n t r o l . d o c u m e n t I n i t i a l i z e d )  
 	 	 	 	 	 D a t a t a b l e . r e f r e s h T a b l e D a t a ( e l e m e n t ) ;  
 	 	 	 }  
 	 	 	 e l s e   {  
 	 	 	 	 e l e m e n t . n e e d R e f r e s h = t r u e ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a l a b e l " : ;  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
                         i f ( d a t a s e t . g e t L e n g t h ( ) = = 0 ) {  
                                 / / d a t a s e t . i n s e r t R e c o r d ( n u l l ) ;  
                                 v a r   n e w R e c o r d = [ ] ;  
 	 	 	 	 / /W( D a t a s e tv剶h~觛凬-v刜SRM嫲_Uc襋eN N*嫲_U  
 	 	 	 	 d a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   n e w R e c o r d ) ;  
 	 	 	 	 i n i t R e c o r d ( n e w R e c o r d ,   d a t a s e t ) ;  
                             	 d a t a s e t . r e c o r d   =   n e w R e c o r d ;  
                         }  
                         _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
  
  
                         / / i f ( ! d a t a s e t . s t a t i c D a t a S o u r c e   | |   C o n t r o l . d o c u m e n t I n i t i a l i z e d )  
 	 	 	         / / _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 e l e m e n t . i s U s e r I n p u t = f a l s e ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a p i l o t " : {  
 	 	 	 _ w i n d o w . D a t a P i l o t . r e f r e s h D a t a P i l o t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " p a g e p i l o t " : {  
 	 	 	 _ w i n d o w . P a g e P i l o t . r e f r e s h P a g e P i l o t ( e l e m e n t ) ;  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " c u s t o m e r p i l o t " : {  
 	 	 	 _ w i n d o w . r e f r e s h C u s t o m e r P i l o t ( e l e m e n t ) ;  
 	 	     b r e a k ;  
 	 	 }  
 	 }  
 	 D a t a s e t . n o t i f y S t a t e C h a n g e d ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   r e s e r v e d ) ;  
 	 i f   ( _ w i n d o w . E d i t o r )   _ w i n d o w . E d i t o r . s i z e D o c k E d i t o r ( ) ;  
 }  
  
 D a t a s e t . n o t i f y F i e l d D a t a C h a n g e d   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   f i e l d ,   r e s e r v e d ) {  
 	 v a r   _ w i n d o w = e l e m e n t . w i n d o w ;  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " d a t a t a b l e " : {  
 	 	 	 v a r   r o w = _ w i n d o w . D a t a t a b l e . g e t T a b l e R o w B y R e c o r d ( e l e m e n t ,   r e c o r d ) ;  
 	 	 	 i f ( r o w   & &   r o w . c e l l s ) {  
 	 	 	 	 f o r ( v a r   i = 0 ;   i < r o w . c e l l s . l e n g t h ;   i + + ) {  
 	 	 	 	 	 v a r   c e l l = r o w . c e l l s [ i ] ;  
 	 	 	 	 	 i f   ( S y s t e m . c o m p a r e T e x t ( c e l l . g e t A t t r i b u t e ( " f i e l d " ) ,   f i e l d . n a m e ) ) {  
 	 	 	 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( c e l l ) ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 i f   ( S y s t e m . c o m p a r e T e x t ( e l e m e n t . g e t A t t r i b u t e ( " f i e l d " ) ,   f i e l d . n a m e ) ) {  
 	 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 	 e l e m e n t . i s U s e r I n p u t = f a l s e ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 	 c a s e   " d a t a l a b e l " : {  
 	 	 	 i f   ( S y s t e m . c o m p a r e T e x t ( e l e m e n t . g e t A t t r i b u t e ( " f i e l d " ) ,   f i e l d . n a m e ) ) {  
 	 	 	 	 _ w i n d o w . C o n t r o l . r e f r e s h E l e m e n t V a l u e ( e l e m e n t ) ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
  
 	 i f   ( _ w i n d o w . E d i t o r )   _ w i n d o w . E d i t o r . s i z e D o c k E d i t o r ( ) ;  
 }  
  
 D a t a s e t . n o t i f y F i e l d S t a t e C h a n g e d   =   f u n c t i o n ( e l e m e n t ,   d a t a s e t ,   r e c o r d ,   f i e l d ,   r e s e r v e d ) {  
 	 s w i t c h   ( e l e m e n t . g e t A t t r i b u t e ( " e x t r a " ) ) {  
 	 	 c a s e   " e d i t o r " : ;  
 	 	 c a s e   " d o c k e d i t o r " : {  
 	 	 	 v a r   e l m t F i e l d = C o n t r o l . g e t E l e m e n t F i e l d ( e l e m e n t ) ;  
 	 	 	 i f   ( e l m t F i e l d = = f i e l d )   {  
 	 	 	 	 e l e m e n t . s e t R e a d O n l y ( ! D a t a s e t . i s F i e l d E d i t a b l e ( d a t a s e t ,   f i e l d ) ) ;  
 	 	 	 }  
 	 	 	 b r e a k ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . c h a n g e M a s t e r R e c o r d S t a t e   =   f u n c t i o n ( d a t a s e t ) {  
 	 v a r   m a s t e r D a t a s e t = d a t a s e t . m a s t e r D a t a s e t ;  
 	 i f   ( m a s t e r D a t a s e t )   {  
 	 	 i f   ( m a s t e r D a t a s e t . r e c o r d . r e c o r d S t a t e = = " n o n e " )   {  
 	 	 	 m a s t e r D a t a s e t . r e c o r d . r e c o r d S t a t e = " m o d i f y " ;  
 	 	 	 D a t a s e t . c h a n g e M a s t e r R e c o r d S t a t e ( m a s t e r D a t a s e t ) ;  
 	 	 }  
 	 }  
 }  
  
 D a t a s e t . f i e l d _ c o u n t   =   f u n c t i o n ( )   {  
 	 r e t u r n   t h i s . f i e l d C o u n t ;  
 }  
  
 D a t a s e t . a d d U p d a t e I t e m   =   f u n c t i o n ( d a t a s e t )   {  
 	 v a r   i t e m = { } ;  
 	 d a t a s e t . u p d a t e I t e m s [ d a t a s e t . u p d a t e I t e m s . l e n g t h ] = i t e m ;  
 	 r e t u r n   i t e m ;  
 }  
  
 D a t a s e t . r e l o a d D a t a   =   f u n c t i o n ( d a t a s e t ,   c a l l b a c k )   {  
 	 v a r   d a t a c m d   =   n e w   Q u e r y C o m m a n d ( ) ;  
 	 d a t a s e t . s t a t i c D a t a S o u r c e = f a l s e ;  
 	 d a t a c m d . s e t D a t a s e t (   d a t a s e t   ) ;  
 	 d a t a c m d . e x e c u t e ( c a l l b a c k ) ;  
 }  
  
 D a t a s e t . c l o n e D a t a s e t   =   f u n c t i o n ( s o u r c e D a t a s e t ,   t a r g e t D a t a s e t ) {  
     t a r g e t D a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
     t a r g e t D a t a s e t . c l e a r D a t a ( ) ;  
     t a r g e t D a t a s e t . d i s a b l e E v e n t s ( ) ;  
     v a r   r e c o r d   =   s o u r c e D a t a s e t . g e t F i r s t R e c o r d ( ) ;  
     w h i l e ( r e c o r d ) {  
         t a r g e t D a t a s e t . i n s e r t R e c o r d ( ) ;  
 	 	 f o r ( v a r   i = 0 ;   i < s o u r c e D a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 	 v a r   f i e l d N a m e = s o u r c e D a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 	 v a r   f i e l d = t a r g e t D a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 	 i f   ( f i e l d )   {  
 	 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   t a r g e t D a t a s e t . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 	 }  
 	 	 }  
         r e c o r d   =   r e c o r d . g e t N e x t R e c o r d ( ) ;  
     }  
     t a r g e t D a t a s e t . e n a b l e E v e n t s ( ) ;  
     t a r g e t D a t a s e t . m o v e F i r s t ( ) ;  
     t a r g e t D a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 }  
  
 D a t a s e t . c l o n e R e c o r d T o D a t a s e t   =   f u n c t i o n ( r e c o r d ,   t a r g e t D a t a s e t ) {  
     t a r g e t D a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
     t a r g e t D a t a s e t . c l e a r D a t a ( ) ;  
  
     t a r g e t D a t a s e t . i n s e r t R e c o r d ( ) ;  
 	 f o r ( v a r   i = 0 ;   i < r e c o r d . d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 v a r   f i e l d N a m e = r e c o r d . d a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 v a r   f i e l d = t a r g e t D a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 i f   ( f i e l d )   {  
 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   t a r g e t D a t a s e t . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 }  
 	 }  
     t a r g e t D a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 }  
  
  
 D a t a s e t . a p p e n d R e c o r d T o D a t a s e t   =   f u n c t i o n ( r e c o r d ,   t a r g e t D a t a s e t ) {  
     / / t a r g e t D a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
     t a r g e t D a t a s e t . i n s e r t R e c o r d ( ) ;  
 	 f o r ( v a r   i = 0 ;   i < r e c o r d . d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 v a r   f i e l d N a m e = r e c o r d . d a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 v a r   f i e l d = t a r g e t D a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 i f   ( f i e l d )   {  
 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   t a r g e t D a t a s e t . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 }  
 	 }  
     / / t a r g e t D a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 }  
  
 D a t a s e t . u p d a t e R e c o r d T o D a t a s e t   =   f u n c t i o n ( r e c o r d ,   t a r g e t D a t a s e t ) {  
     / / t a r g e t D a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	 f o r ( v a r   i = 0 ;   i < r e c o r d . d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 v a r   f i e l d N a m e = r e c o r d . d a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 v a r   f i e l d = t a r g e t D a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 i f   ( f i e l d )   {  
 	 	 	 v a r   v a l u e = r e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   t a r g e t D a t a s e t . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 }  
 	 }  
     / / t a r g e t D a t a s e t . e n a b l e C o n t r o l s ( ) ;  
 }  
  
 D a t a s e t . c l o n e R e c o r d   =   f u n c t i o n ( s o u r c e R e c o r d ,   t a r g e t R e c o r d ) {  
 	 f o r ( v a r   i = 0 ;   i < s o u r c e R e c o r d . d a t a s e t . f i e l d s . f i e l d C o u n t ;   i + + ) {  
 	 	 v a r   f i e l d N a m e = s o u r c e R e c o r d . d a t a s e t . g e t F i e l d ( i ) . n a m e ;  
 	 	 v a r   f i e l d = t a r g e t R e c o r d . d a t a s e t . g e t F i e l d ( f i e l d N a m e ) ;  
 	 	 i f   ( f i e l d )   {  
 	 	 	 v a r   v a l u e = s o u r c e R e c o r d . g e t V a l u e ( f i e l d N a m e ) ;  
 	 	 	 i f   ( t y p e o f ( v a l u e ) ! = " u n d e f i n e d " )   t a r g e t R e c o r d . s e t V a l u e ( f i e l d N a m e ,   v a l u e ) ;  
 	 	 }  
 	 }  
 }  
  
 D a t e . p r o t o t y p e . t o S t r i n g   =   f u n c t i o n ( )   {  
 	 r e t u r n   t h i s . g e t T i m e ( ) . t o S t r i n g ( ) ;  
 }  
  
  
 / /   C o m m a n d  
 f u n c t i o n   C o m m a n d ( i d )   {  
 	 t h i s . _ i d   =   i d ;  
 }  
  
 C o m m a n d . p r o t o t y p e . e x e c u t e   =   f u n c t i o n ( )   { }  
  
 C o m m a n d . p r o t o t y p e . g e t I d   =   f u n c t i o n ( )   {  
 	 r e t u r n   t h i s . _ i d ;  
 }  
  
 / /   Q u e r y C o m m a n d  
 f u n c t i o n   D o w n L o a d D a t a C o m m a n d ( i d )   {  
 	 t h i s . _ d a t a s e t   =   n u l l ; / /T宔pcng鍕鈤裑歷 D a t a s e t[鶎a  
 }  
  
 D o w n L o a d D a t a C o m m a n d . p r o t o t y p e   =   n e w   C oo m m a n d ( ) ; / /~鏱侁 C o m m a n d|{  
  
 D o w n L o a d D a t a C o m m a n d . p r o t o t y p e . e x e c u t e   =   f u n c t i o n ( _ c a l l B a c k ) {  
 	 i f (   ! t h i s . _ d a t a s e t . l o a d D a t a A c t i o n     | |   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n   = =   " "   | |  
 	 	 t h i s . _ d a t a s e t . l o a d D a t a A c t i o n   = =   " u n d i f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
 	 i f (   ! t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d     | |   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " "   | |  
 	 	 t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " u n d i f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
 	 v a r   a s y n c   =   f a l s e ;  
 	 i f ( t y p e o f ( _ c a l l B a c k ) ! = " u n d e f i n e d "   & &   _ c a l l B a c k ! = n u l l )  
 	     a s y n c   =   t r u e ;  
 	 v a r   a j a x C a l l   =   n e w   N D A j a x C a l l ( a s y n c ) ; / / f a l s eTke  
  
 	 / /兎S謆@g	v凷耬pS耬p?徢 d a t a s e t~腘鯿蠴泇別筶諎?nS颪鍕?ne鄸PN*  
 	 / /S耬pOFf/S耬pv刱!^廮艠{O輯罷実Rh~腘鯿蠴沢R別筶誺凷耬pN 侓0  
         v a r   p a r a m e t e r s   =   [ ] ;  
         v a r   p s   =   t h i s . _ d a t a s e t . _ p a r a m e t e r s ;  
         f o r (   v a r   i   =   0 ;   i   <   p s . c o u n t ( ) ;   i   + +   ) {  
                 v a r   p a r a m e t e r N a m e   =   p s . i n d e x T o N a m e ( i ) ;  
         	 v a r   p a r a m e t e r T y p e   =   p s . g e t D a t a T y p e (   p a r a m e t e r N a m e   ) ;  
         	 v a r   p a r a m e t e r V a l u e   =   p s . g e t V a l u e (   p a r a m e t e r N a m e   ) ;  
         	 i f (   p a r a m e t e r T y p e   = =   " o b j e c t "   ) {  
         	 	 p a r a m e t e r s [ i ]   =   p a r a m e t e r V a l u e   ;  
         	 } e l s e {  
 	         	 v a r   v a l u e   =   p a r s e V a l u e (   p a r a m e t e r V a l u e ,   p a r a m e t et e r T y p e   ) ;  
         	 	 p a r a m e t e r s [ i ]   =   v a l u e   ;  
         	 }  
         }  
  
         v a r   r e t u r n X M L   =   " " ;  
         / / = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = C a l l   B a c k   F u n c t i o n   S t a r t   H e r e  
         v a r   c a l l B a c k   =   f u n c t i o n   ( r e p l y )   {  
 	 	 / /兎S謌Rhz飶訴辷 X M Lh<_v刐W{&N2  
 	 	 r e t u r n X M L   =   r e p l y . g e t R e s u l t ( ) ;  
 	 	 i f ( t y p e o f ( _ c a l l B a c k ) ! = " u n d e f i n e d "   & &   _ c a l l B a c k ! = n u l l ) {  
 	 	     _ c a l l B a c k ( r e t u r n X M L ) ;  
 	 	 }  
         }  
         a j a x C a l l . r e m o t e C a l l ( t h i s . _ d a t a s e t . l o a d D a t a A c t i o n ,   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d ,   p a r a m e t e r s ,   c a l l B a c k , t h i s . _ d a t a s e t . _ q u e r y T y p e , t h i s . _ d a t a s e t . _ q u e r y F i e l d s ) ;  
 	 r e t u r n   r e t u r n X M L ;  
 }  
  
 D o w n L o a d D a t a C o m m a n d . p r o t o t y p e . g e t D a t a s e t   =   f u n c t i o n ( ) {  
 	 r e t u r n   t h i s . _ d a t a s e t   ;  
 }  
  
 D o w n L o a d D a t a C o m m a n d . p r o t o t y p e . s e t D a t a s e t   =   f u n c t i o n (   d a t a s e t   ) {  
 	 t h i s . _ d a t a s e t   =   d a t a s e t   ;  
 }  
 f u n c t i o n   p a r s e V a l u e (   v a l u e ,   t y p e   ) {  
 	 i f (   t y p e   = =   " i n t "   ) {  
 	 	 r e t u r n   p a r s e I n t (   v a l u e   )   ;  
 	 } e l s e {  
 	         r e t u r n   " "   +   v a l u e   ;  
 	 }  
 }  
  
 / /   Q u e r y C o m m a n d  
 f u n c t i o n   Q u e r y C o m m a n d ( i d )   {  
 	 t h i s . _ d a t a s e t   =   n u l l ; / /T宔pcng鍕鈤裑歷 D a t a s e t[鶎a  
 }  
  
 Q u e r y C o m m a n d . p r o t o t y p e   =   n e w   C o m m a n d ( ) ; / /~鏱侁 C o m m a n d|{  
  
 Q u e r y C o m m a n d . p r o t o t y p e . e x e e c u t e   =   f u n c t i o n ( _ _ c a l l b a c k ) {  
 	 i f (   ! t h i s . _ d a t a s e t . l o a d D a t a A c t i o n     | |   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n   = =   " "   | |  
 	 	 t h i s . _ d a t a s e t . l o a d D a t a A c t i o n   = =   " u n d e f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
 	 i f (   ! t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d     | |   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " "   | |  
 	 	 t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d   = =   " u n d e f i n e d "   ) {  
 	 	 r e t u r n   " " ;  
 	 }  
 	 v a r   a j a x C a l l   =   n e w   N D A j a x C a l l ( t h i s . _ d a t a s e t . a s y n c ) ; / / t r u eN:_ke  
         v a r   p a r a m e t e r s   =   " " ;  
 	 / /兎S謆@g	v凷耬pS耬p?徢 d a t a s e t~腘鯿蠴泇別筶諎?nS颪鍕?ne鄸PN*  
 	 / /S耬pOFf/S耬pv刱!^廮艠{O輯罷実Rh~腘鯿蠴沢R別筶誺凷耬pN 侓0  
         v a r   p a r a m e t e r s   =   [ ] ;  
         v a r   p s   =   t h i s . _ d a t a s e t . _ p a r a m e t e r s ;  
  
         f o r (   v a r   i   =   0 ;   i   <   p s . c o u n t ( ) ;   i   + +   ) {  
         	 v a r   p a r a m e t e r N a m e   =   p s . i n d e x T o N a m e ( i ) ;  
         	 v a r   p a r a m e t e r T y p e   =   p s . g e t D a t a T y p e (   p a r a m e t e r N a m e   ) ;  
         	 v a r   p a r a m e t e r V a l u e   =   p s . g e t V a l u e (   p a r a m e t e r N a m e   ) ;  
         	 i f (   p a r a m e t e r T y p e   = =   " o b j e c t "   ) {  
         	 	 p a r a m e t e r s [ i ]   =   p a r a m e t e r V a l u e   ;  
         	 } 	 e l s e {  
 	         	 v a r   v a l u e   =   p a r s e V a l u e (   p a r a m e t e r V a l u e ,   p a r a m e t e r T y p e   ) ;  
 	         	 p a r a m e t e r s [ i ]   =   v a l u e   ;  
         	 }  
         }  
         / / = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = C a l l   B a c k   F u n c t i  i o n   S t a r t   H e r e  
         v a r   r e t u r n X M L   =   " " ;  
         v a r   t e m p D a t a s e t   =   t h i s . _ d a t a s e t   ;  
  
         v a r   c a l l B a c k   =   f u n c t i o n   ( r e p l y )   {  
 	 	 / /兎S謌Rhz飶訴辷 X M Lh<_v刐W{&N2  
 	 	 r e t u r n X M L   =   r e p l y . g e t R e s u l t ( ) ;  
  
 	 	 t e m p D a t a s e t . d i s a b l e C o n t r o l s ( ) ;  
  
 	 	 i f   ( t e m p D a t a s e t . x m l F o r m a t   = =   " r e c o r d s " )   { / /Y俫渇/ < r e c o r d s >h<_v X M L  
 	 	 	 v a r   d a t a D o c   =   X m l D o c u m e n t . c r e a t e ( ) ;  
  
 	 	 	 i f (   ! t e m p D a t a s e t . s t a t i c D a t a S o u r c e   ) { / /Y俫渇/W(gRhz飶袌L  
 	 	 	 	 	 d a t a D o c . a s y n c   =   f a l s e ;  
 	 	 	 	 	 d a t a D o c . l o a d X M L ( r e t u r n X M L ) ;  
 	 	 	 	 	 v a r   r o o t   =   d a t a D o c . d o c u m e n t E l e m e n t ;  
 	 	 	 	 	 i f ( n u l l = = r o o t ) r e t u r n ; / / 2 0 1 0 . 9 . 1 4  s媐%fO頴9  
 	 	 	 	 	 v a r   t o t a l C o u n t   =   r o o t . g e t A t t r i b u t e ( " t o t a l C o u n t " ) ;  
 	 	 	 	 	 v a r   p a g e I n d e x   =   r o o t . g e t A t t r i b u t e ( " p a g e I n d e x " ) ;  
 	 	 	 	 	 v a r   p a g e C o u n t   =   r o o t . g e t A t t r i b u t e ( " p a g e C o u n t " ) ;  
 	 	 	 	 	 t e m p D a t a s e t . r e c o r d C o u n t   =   t o t a l C o u n t   ;  
 	 	 	 	 	 / /f鬳 d a t a s e t_SRMv剗"_榰ep  
 	 	 	 	 	 t e m p D a t a s e t . p a g e I n d e x = S y s t e m . g e t I n t ( p a g e I n d e x ) ;  
 	 	 	 	 	 / /f鬳 d a t a s e tv刞;榰ep  
 	 	 	 	 	 t e m p D a t a s e t . p a g e C o u n t = S y s t e m . g e t I n t ( p a g e C o u n t ) ;  
 	 	 	 	 	 t e m p D a t a s e t . c l e a r D a t a ( ) ;  
 	 	 	 }  
 	 	 	 v a r   c u r r e n t   =   D a t a s e t . a p p e n d F r o m X m l ( t e m p D a t a s e t ,   d a t a D o c . d o c u m e n t E l e m e n t ,   t r u e ) ;  
 	 	 	 t e m p D a t a s e t . p r e p a r e d   d   =   t r u e ;  
 	 	 } e l s e   i f   ( t e m p D a t a s e t . x m l F o r m a t   = =   " i t e m s " )   { / /Y俫渇/ < i t e m s >h<_v X M L  
 	 	 	 	 i f (   ! t e m p D a t a s e t . s t a t i c D a t a S o u r c e   ) {  
 	 	 	 	 	 / /Y俫渇/W(gRhz飶袌L  b芌 r e t u r n V a l u e s  
 	 	 	 	 	 v a r   r e t u r n V a l u e s   =   r e t u r n X M L ;  
 	 	 	 	 	 f o r   ( i   =   0 ;   i   <   r e t u r n V a l u e s . l e n g t h ;   i + + )   {  
 	 	 	 	 	         v a r   r e c o r d   =   [ r e t u r n V a l u e s [ i ] . a t t r V a l u e , r e t u r n V a l u e s [ i ] . a t t r V a l u e I d , r e t u r n V a l u e s [ i ] . a t t r V a l u e D e s c ] ;  
 	 	 	 	 	 	 t e m p D a t a s e t . i n s e r t U n i t ( " e n d " ,   n u l l ,   r e c o r d ) ;  
 	 	 	 	 	 	 i n i t R e c o r d ( r e c o r d ,   t e m p D a t a s e t ) ;  
 	 	 	 	 	 }  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 i f   ( c u r r e n t )   {  
 	 	 	 	 t e m p D a t a s e t . s e t R e c o r d ( c u r r e n t ) ;  
 	 	 	 }   e l s e   {  
 	 	 	 	 i f   ( t e m p D a t a s e t . p a g e I n d e x   = =   1   | |   ! t e m p D a t a s e t . a u t o L o a d P a g e )   {  
 	 	 	 	 	 t e m p D a t a s e t . m o v e F i r s t ( ) ;  
 	 	 	 	 }   e l s e   {  
 	 	 	 	 	 t e m p D a t a s e t . s e t R e c o r d ( t e m p D a t a s e t . g e t F i r s t R e c o r d ( ) ) ;  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 i f   ( ! t e m p D a t a s e t . r e c o r d )   {  
 	 	 	 	 i f   ( t e m p D a t a s e t . i n s e r t O n E m p t y   & &   ! t e m p D a t a s e t . r e a d O n l y )   {  
 	 	 	 	 	 t e m p D a t a s e t . i n s e r t R e c o r d ( ) ;  
 	 	 	 	 }  
 	 	 	 }  
 	 	 	 t e m p D a t a s e t . e n a b l e C o n t r o l s ( ) ;  
  
 	     i f ( t y p e o f ( _ _ c a l l b a c k ) ! = " u n d e f i n e d "   & &   _ _ c a l l b a c k ! = n u l l ) {  
 	         _ _ c a l l b a c k ( ) ;  
 	     }  
         }  
  
         i f ( t h i s . _ d a t a s e t . m a s k C o n t r o l ) {  
          	 v a r   a j a x C a l l F o r M a s k F i e l d s   =   n e w   N D A j a x C a l l ( t h i s . _ d a t a s e t . a s y n c ) ;  
         	 a j a x C a l l F o r M a s k F i e l d s . r e m o t e C a l l ( " P r i v i l e g e S e r v i c e " ,   " g e t C u s t C t r l D a t a I n f o " ,   [ G l o b a l . p a g e I d ,   t h i s . _ d a t a s e t . i d ,   G l o b a l . c u s t T y p e I d ] ,   f u n c t i o n ( r e p l y ) {  
         	 	 t e m p D a t a s e t . m a s k C o n t r o l F i e l d s   =   r e p l y . g e t R e s u l t ( ) ;  
         	 	 / / t e m p D a t a s e t . m a s k C o n t r o l F i e l d s   =   [ " c u s t N a m e " ,   " c u s t D e p t T y p e " ,   " t y p e F l a g " ,   " c o n t a c t N a m e " , " a c c t I d " ,   " c u s t L o c a t i o n " ,   " i m p o r t a n c e L e v e l " ,   " l a t e n t V i p F l a g " ,   " c e r t i f i c a t e N o " ] ;  
         	 	 / / t e m p D a t a s e t . r e f r e s h F i e l d M a s k C o n t r o l s ( ) ;  
         	 	 a j a x C a l l . r e m o t e C a l l ( t e m p D a t a s e t . l o a d D a t a A c t i o n ,   t e m p D a t a s e t . l o a d D a t a A c t i o n M e t h o d ,   p a r a m e t e r s ,   c a l l B a c k , t e m p D a t a s e t . _ q u e r y T y p e , t e m p D a t a s e t . _ q u e r y F i e l d s ) ;  
         	 } ) ;  
         }  
         e l s e {  
         	 a j a x C a l l . r e m o t e C a l l ( t h i s . _ d a t a s e t . l o a d D a t a A c t i o n ,   t h i s . _ d a t a s e t . l o a d D a t a A c t i o n M e t h o d ,   p a r a m e t e r s ,   c a l l B a c k , t h i s . _ d a t a s e t . _ q u e r y T y p e , t h i s . _ d a t a s e t . _ q u e r y F i e l d s ) ;  
 	 }  
 }  
  
  
 Q u e r y C o m m a n d . p r o t o t y p e . g e t D a t a s e t   =   f u n c t i o n ( ) {  
 	 r e t u r n   t h i s . _ d a t a s e t ;  
 }  
  
 Q u e r y C o m m a n d . p r o t o t y p e . s e t D a t a s e t   =   f u n c t i o n ( d a t a s e t )   {  
 	 t h i s . _ d a t a s e t   =   d a t a s e t ;  
 }  
