import request from '@/utils/request'

// 查询媒体广告位列表
export function listMediaAd(query) {
  return request({
    url: '/flow/mediaAd/list',
    method: 'get',
    params: query
  })
}

// 查询媒体广告位详细
export function getMediaAd(id) {
  return request({
    url: '/flow/mediaAd/' + id,
    method: 'get'
  })
}

// 新增媒体广告位
export function addMediaAd(data) {
  return request({
    url: '/flow/mediaAd',
    method: 'post',
    data: data
  })
}

// 修改媒体广告位
export function updateMediaAd(data) {
  return request({
    url: '/flow/mediaAd',
    method: 'put',
    data: data
  })
}

// 删除媒体广告位
export function delMediaAd(id) {
  return request({
    url: '/flow/mediaAd/' + id,
    method: 'delete'
  })
}
